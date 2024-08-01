package com.wanted.pre_onboarding.repository;

import com.wanted.pre_onboarding.domain.Company;
import com.wanted.pre_onboarding.domain.JobPosting;
import com.wanted.pre_onboarding.domain.JobPostingUser;
import com.wanted.pre_onboarding.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Slf4j
class JobPostingRepositoryTest {
   @Autowired
   JobPostingRepository jobPostingRepository;
   @Autowired

   CompanyRepository companyRepository;
   @Autowired
   JobPostingUserRepository postingUserRepository;
   @Autowired
   UserRepository userRepository;

   private Long jobPostingId;
   private UUID companyId;
   private UUID userId;

    void createCompanyAndJobPosting(String companyName, String country, String location, String position, int compensation, String description, String technology){

        Company company = createCompany(companyName,country,location);
        JobPosting posting = createJobPosting(company,position,compensation,description,technology);

    }

    Company createCompany(String companyName, String country, String location) {
        Company company = Company.builder()
                .companyName(companyName)
                .country(country)
                .location(location)
                .build();
        companyRepository.save(company);
        return company;
    }

    JobPosting createJobPosting(Company company,String position, int compensation, String description, String technology) {
        JobPosting posting = JobPosting.builder()
                .position(position)
                .compensation(compensation)
                .description(description)
                .technology(technology)
                .company(company)
                .build();
        jobPostingRepository.save(posting);
        return posting;
    }

    @BeforeEach
    @Transactional
    void beforeEach() {
        jobPostingRepository.deleteAll();

        Company c1 = createCompany("원티드","한국","서울");


        User user = User.builder().email("sun@gmail.com").password("1234").name("김선희").build();
        userRepository.save(user);

        JobPosting posting = createJobPosting(c1,"백엔드 주니어 개발자",1000000,"원티드랩에서 백엔드 주니어 개발자를 채용합니다. 자격요건은..","Python");
         createJobPosting(c1,"백엔드 주니어 개발자2",1000000,"원티드랩에서 백엔드 주니어 개발자를 채용합니다. 자격요건은..","Python");


         jobPostingId = posting.getId();
        companyId = c1.getId();
        userId = user.getUserId();
    }

    @Test
    @Transactional
    @DisplayName("채용공고 등록")
     void enrollPosting() {
        JobPosting posting = JobPosting.builder()
                .position("백엔드 주니어 개발자")
                .compensation(1000000)
                .description("원티드랩에서 백엔드 주니어 개발자를 채용합니다. 자격요건은..")
                .technology("Python")
                .build();
        jobPostingRepository.save(posting);
        assertThat(jobPostingRepository.findById(posting.getId())).isNotNull();
    }

    @Test
    @Transactional
    @DisplayName("채용공고 수정: 채용보상금, 채용 내용")
    void editPosting() {
        jobPostingRepository.findById(jobPostingId).ifPresent(jobPosting -> {
            log.info("id {} ", jobPosting.getId());
            JobPosting data = jobPostingRepository.findById(jobPostingId).get();
            data.update("백엔드 주니어 개발자", 1500000, "원티드랩에서 백엔드 주니어 개발자를 '적극' 채용합니다. 자격요건은..", "Python", "한국", "서울");

        });

        jobPostingRepository.findById(jobPostingId).ifPresent(jobPosting -> {
            JobPosting data = jobPostingRepository.findById(jobPostingId).get();
            assertThat(data.getDescription()).contains("적극");
            assertThat(data.getCompensation()).isEqualTo(1500000);
        });
    }

    @Test
    @Transactional
    @DisplayName("채용공고 수정: 사용 기술 수정")
    void editPosting2() {
        Optional<JobPosting> getPosting = jobPostingRepository.findById(jobPostingId);

        getPosting.ifPresent(jobPosting -> {
            log.info("id {} ", jobPosting.getId());
            JobPosting data =  getPosting.get();
            data.update("백엔드 주니어 개발자",1000000,"원티드랩에서 백엔드 주니어 개발자를 채용합니다. 자격요건은..","Django", "한국", "서울");

            assertThat(data.getTechnology()).isEqualTo("Django");
        });

        jobPostingRepository.findById(jobPostingId).ifPresent(jobPosting -> {
            JobPosting data = jobPostingRepository.findById(jobPostingId).get();

            assertThat(data.getTechnology()).isEqualTo("Django");

        });
    }

    @Test
    @Transactional
    @DisplayName("채용공고 삭제")
    void deletePosting() {
        jobPostingRepository.deleteById(jobPostingId);

        Optional<JobPosting> getPosting = jobPostingRepository.findById(jobPostingId);

        assertThat(getPosting).isEmpty();
    }

    @Test
    @DisplayName("채용공고 목록")
    void getPostingList(){
        log.info("목록 {}", jobPostingRepository.findAllWithCompany());
        assertThat(jobPostingRepository.findAllWithCompany().size()).isEqualTo(2);
    }

    @Test
    @DisplayName("채용공고 목록(회사 확인)")
    void getPostingList2(){
        log.info("목록 {}", jobPostingRepository.findAllWithCompany());
        for (int i = 0; i < jobPostingRepository.findAllWithCompany().size(); i++) {
            log.info("company {} ",jobPostingRepository.findAllWithCompany().get(i).getCompany());
            assertThat(jobPostingRepository.findAllWithCompany().get(i).getCompany()).isNotNull();
        }
    }

    @Test
    @Transactional
    @DisplayName("원티드 검색")
    void searchPosting() {
        jobPostingRepository.deleteAll();

        createCompanyAndJobPosting("원티드랩","한국","서울","백엔드 주니어 개발자",1500000,"백엔드 주니어 개발자를 채용합니다. 자격요건은..","Python");
        createCompanyAndJobPosting("원티드코리아","한국","부산","프론트엔드 개발자",500000,"프론트엔드 주니어 개발자를 채용합니다. 자격요건은..","javascript");
        createCompanyAndJobPosting("네이버","한국","판교","Django 백엔드 개발자",1000000,"Django 주니어 개발자를 채용합니다. 자격요건은..","Django");
        createCompanyAndJobPosting("카카오","한국","판교","Django 백엔드 개발자",500000,"Django 주니어 개발자를 채용합니다. 자격요건은..","Python");

        log.info("검색 결과 {} ", jobPostingRepository.search("원티드"));
        assertThat(jobPostingRepository.search("원티드").size()).isEqualTo(2);
    }

    @Test
    @Transactional
    @DisplayName("Django 검색")
    void searchPosting2() {
        jobPostingRepository.deleteAll();
        createCompanyAndJobPosting("원티드랩","한국","서울","백엔드 주니어 개발자",1500000,"백엔드 주니어 개발자를 채용합니다. 자격요건은..","Python");
        createCompanyAndJobPosting("원티드코리아","한국","부산","프론트엔드 개발자",500000,"프론트엔드 주니어 개발자를 채용합니다. 자격요건은..","javascript");
        createCompanyAndJobPosting("네이버","한국","판교","Django 백엔드 개발자",1000000,"Django 주니어 개발자를 채용합니다. 자격요건은..","Django");
        createCompanyAndJobPosting("카카오","한국","판교","Django 백엔드 개발자",500000,"Django 주니어 개발자를 채용합니다. 자격요건은..","Python");

        log.info("검색 결과 {} ", jobPostingRepository.search("Django"));
        assertThat(jobPostingRepository.search("Django").size()).isEqualTo(2);
    }

    @Test
    @DisplayName("특정 회사가 올린 공고 조회")
    void getDetails(){
        Optional<JobPosting> getPosting = jobPostingRepository.findById(jobPostingId);

        getPosting.ifPresent((posting)->
                log.info("posting company {} ",posting.getCompany()));

        log.info("companyId {} ", companyId);

        log.info("결과는? {} ", jobPostingRepository.findAllByCompanyId(companyId));

        assertThat(jobPostingRepository.findAllByCompanyId(companyId).size()).isEqualTo(2);
    }

    @Test
    @DisplayName("채용 공고 지원")
    @Transactional
    void apply() {
        User user = null;
        JobPosting jobPosting = null;
        if (userRepository.findById(userId).isPresent() && jobPostingRepository.findById(jobPostingId).isPresent()){

            user = userRepository.findById(userId).get();
            jobPosting = jobPostingRepository.findById(jobPostingId).get();

            JobPostingUser jobPostingUser = JobPostingUser.builder().user(user).jobPosting(jobPosting).build();
            postingUserRepository.save(jobPostingUser);

            assertThat(postingUserRepository.findById(jobPostingUser.getPostingUserId())).isNotNull();

        }

    }
}