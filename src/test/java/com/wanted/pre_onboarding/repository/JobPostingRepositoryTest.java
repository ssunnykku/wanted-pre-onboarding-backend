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


    @BeforeEach
    @Transactional
    void beforeEach() {
        jobPostingRepository.deleteAll();
        Company c1 = Company.builder().companyName("원티드").location("서울").country("한국").build();
        companyRepository.save(c1);

        User user = User.builder().email("sun@gmail.com").password("1234").name("김선희").build();
        userRepository.save(user);

         JobPosting posting = JobPosting.builder()
                 .position("백엔드 주니어 개발자")
                 .compensation(1000000)
                 .description("원티드랩에서 백엔드 주니어 개발자를 채용합니다. 자격요건은..")
                 .technology("Python")
                 .company(c1)
                 .build();
         jobPostingRepository.save(posting);

        JobPosting posting2 = JobPosting.builder()
                .position("백엔드 주니어 개발자2")
                .compensation(1000000)
                .description("원티드랩에서 백엔드 주니어 개발자를 채용합니다. 자격요건은..")
                .technology("Python")
                .company(c1)
                .build();

        jobPostingRepository.save(posting2);

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

        Company c1 = Company.builder()
                .companyName("원티드랩")
                .country("한국")
                .location("서울")
                .build();

        Company c2 = Company.builder()
                .companyName("원티드코리아")
                .country("한국")
                .location("부산")
                .build();

        Company c3 = Company.builder()
                .companyName("네이버")
                .country("한국")
                .location("판교")
                .build();

        Company c4 = Company.builder()
                .companyName("카카오")
                .country("한국")
                .location("판교")
                .build();

        companyRepository.save(c1);
        companyRepository.save(c2);
        companyRepository.save(c3);
        companyRepository.save(c4);

        JobPosting posting = JobPosting.builder()
                .position("백엔드 주니어 개발자")
                .compensation(1500000)
                .description("백엔드 주니어 개발자를 채용합니다. 자격요건은..")
                .technology("Python")
                .company(c1)
                .build();

        JobPosting posting2 = JobPosting.builder()
                .position("프론트엔드 개발자")
                .compensation(500000)
                .description("프론트엔드 주니어 개발자를 채용합니다. 자격요건은..")
                .technology("javascript")
                .company(c2)
                .build();

        JobPosting posting3 = JobPosting.builder()
                .position("Django 백엔드 개발자")
                .compensation(1000000)
                .description("Django 주니어 개발자를 채용합니다. 자격요건은..")
                .technology("Django")
                .company(c3)
                .build();

        JobPosting posting4 = JobPosting.builder()
                .position("Django 백엔드 개발자")
                .compensation(500000)
                .description("Django 주니어 개발자를 채용합니다. 자격요건은..")
                .technology("Python")
                .company(c4)
                .build();

        jobPostingRepository.save(posting);
        jobPostingRepository.save(posting2);
        jobPostingRepository.save(posting3);
        jobPostingRepository.save(posting4);

        log.info("검색 결과 {} ", jobPostingRepository.search("원티드"));
        assertThat(jobPostingRepository.search("원티드").size()).isEqualTo(2);
    }

    @Test
    @Transactional
    @DisplayName("Django 검색")
    void searchPosting2() {
        jobPostingRepository.deleteAll();

        Company c1 = Company.builder()
                .companyName("원티드랩")
                .country("한국")
                .location("서울")
                .build();

        Company c2 = Company.builder()
                .companyName("원티드코리아")
                .country("한국")
                .location("부산")
                .build();

        Company c3 = Company.builder()
                .companyName("네이버")
                .country("한국")
                .location("판교")
                .build();

        Company c4 = Company.builder()
                .companyName("카카오")
                .country("한국")
                .location("판교")
                .build();

        companyRepository.save(c1);
        companyRepository.save(c2);
        companyRepository.save(c3);
        companyRepository.save(c4);

        JobPosting posting = JobPosting.builder()
                .position("백엔드 주니어 개발자")
                .compensation(1500000)
                .description("백엔드 주니어 개발자를 채용합니다. 자격요건은..")
                .technology("Python")
                .company(c1)
                .build();

        JobPosting posting2 = JobPosting.builder()
                .position("프론트엔드 개발자")
                .compensation(500000)
                .description("프론트엔드 주니어 개발자를 채용합니다. 자격요건은..")
                .technology("javascript")
                .company(c2)
                .build();

        JobPosting posting3 = JobPosting.builder()
                .position("Django 백엔드 개발자")
                .compensation(1000000)
                .description("Django 주니어 개발자를 채용합니다. 자격요건은..")
                .technology("Django")
                .company(c3)
                .build();

        JobPosting posting4 = JobPosting.builder()
                .position("Django 백엔드 개발자")
                .compensation(500000)
                .description("Django 주니어 개발자를 채용합니다. 자격요건은..")
                .technology("Python")
                .company(c4)
                .build();

        jobPostingRepository.save(posting);
        jobPostingRepository.save(posting2);
        jobPostingRepository.save(posting3);
        jobPostingRepository.save(posting4);

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