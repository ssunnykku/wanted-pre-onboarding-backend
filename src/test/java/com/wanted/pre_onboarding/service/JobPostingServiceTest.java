package com.wanted.pre_onboarding.service;

import com.wanted.pre_onboarding.domain.Company;
import com.wanted.pre_onboarding.domain.JobPosting;
import com.wanted.pre_onboarding.dto.JobPostingDTO;
import com.wanted.pre_onboarding.repository.CompanyRepository;
import com.wanted.pre_onboarding.repository.JobPostingRepository;
import com.wanted.pre_onboarding.util.TestUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Slf4j
class JobPostingServiceTest {
    @Autowired
    JobPostingService jobPostingService;
    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    JobPostingRepository jobPostingRepository;

    @Autowired
    TestUtil testUtil;

    @BeforeEach
    @Transactional
    void setUpDB() {
        jobPostingRepository.deleteAll();

    }

    @Test
    @Transactional
    @DisplayName("채용공고 등록")
    void addJobPosting() {

        Company company = Company.builder()
                .companyName("원티드랩")
                .country("한국")
                .location("서울")
                .build();

        companyRepository.save(company);

        JobPostingDTO jobPosting = JobPostingDTO.builder()
                .position("백엔드 주니어 개발자")
                .compensation(1000000)
                .description("원티드랩에서 백엔드 주니어 개발자를 채용합니다. 자격요건은..")
                .skill("Python")
                .companyId(company.getId())
                .build();

        jobPostingService.addJobPosting(jobPosting);

        List<JobPosting> postingList = jobPostingRepository.findAll();

        assertThat(postingList.size()).isEqualTo(1);

        JobPosting posting = postingList.get(0);

        assertThat(posting.getPosition()).isEqualTo(jobPosting.getPosition());
        assertThat(posting.getCompensation()).isEqualTo(jobPosting.getCompensation());
        assertThat(posting.getSkill()).isEqualTo(jobPosting.getSkill());
        assertThat(posting.getDescription()).isEqualTo(jobPosting.getDescription());
        assertThat(posting.getCompany().getId()).isEqualTo(jobPosting.getCompanyId());

    }

    @Test
    @DisplayName("채용공고 수정: 채용보상금, 채용내용")
    void editJobPosting() {

        testUtil.createCompanyAndJobPosting("원티드랩", "한국","서울",
                "백엔드 주니어 개발자",1000000,
                "백엔드 주니어 개발자를 채용합니다. 자격요건은..","Python");

        List<JobPosting> postingList = jobPostingRepository.findAll();

        assertThat(postingList.size()).isEqualTo(1);

        JobPosting posting = postingList.get(0);

        log.info("{}",posting);
        JobPostingDTO editJobPosting = JobPostingDTO.builder()
                .jobPostingId(posting.getId())
                .position("백엔드 주니어 개발자")
                .compensation(1500000)
                .description("원티드랩에서 백엔드 주니어 개발자를 '적극' 채용합니다. 자격요건은..")
                .skill("Python")
                .build();

        jobPostingService.editJobPosting(posting.getId(), editJobPosting);

        List<JobPosting> editPostingList = jobPostingRepository.findAll();

        assertThat(editPostingList.size()).isEqualTo(1);

        JobPosting editPosting = editPostingList.get(0);

        assertThat(editPosting.getCompensation()).isEqualTo(editJobPosting.getCompensation());
        assertThat(editPosting.getDescription()).isEqualTo(editJobPosting.getDescription());

    }

    @Test
    @DisplayName("채용공고 수정: 사용기술")
    void editJobPosting2() {

        testUtil.createCompanyAndJobPosting("원티드랩", "한국","서울",
                "백엔드 주니어 개발자",1000000,
                "백엔드 주니어 개발자를 채용합니다. 자격요건은..","Python");

        List<JobPosting> postingList = jobPostingRepository.findAll();

        assertThat(postingList.size()).isEqualTo(1);

        JobPosting posting = postingList.get(0);

        log.info("{}",posting);
        JobPostingDTO editJobPosting = JobPostingDTO.builder()
                .jobPostingId(posting.getId())
                .position("백엔드 주니어 개발자")
                .compensation(1500000)
                .description("원티드랩에서 백엔드 주니어 개발자를 '적극' 채용합니다. 자격요건은..")
                .skill("Django")
                .build();

        jobPostingService.editJobPosting(posting.getId(), editJobPosting);

        List<JobPosting> editPostingList = jobPostingRepository.findAll();

        assertThat(editPostingList.size()).isEqualTo(1);

        JobPosting editPosting = editPostingList.get(0);

        assertThat(editPosting.getSkill()).isEqualTo(editJobPosting.getSkill());

    }

    @Test
    @DisplayName("채용공고 삭제")
    void removeJobPosting() {
        testUtil.createCompanyAndJobPosting("원티드랩", "한국","서울",
                "백엔드 주니어 개발자",1000000,
                "백엔드 주니어 개발자를 채용합니다. 자격요건은..","Python");

        List<JobPosting> postingList = jobPostingRepository.findAll();

        assertThat(postingList.size()).isEqualTo(1);

        JobPosting posting = postingList.get(0);

        jobPostingService.removeJobPosting(posting.getId());

        assertThat(jobPostingRepository.findAll().size()).isEqualTo(0);
    }

    @Test
    @DisplayName("채용공고 목록")
    void getJobPostingList() {
        testUtil.createCompanyAndJobPosting("원티드랩","한국","서울",
                "백엔드 주니어 개발자",1500000,
                "백엔드 주니어 개발자를 채용합니다. 자격요건은..","Python");
        testUtil.createCompanyAndJobPosting("원티드코리아","한국","부산",
                "프론트엔드 개발자",500000,
                "프론트엔드 주니어 개발자를 채용합니다. 자격요건은..","javascript");
        testUtil.createCompanyAndJobPosting("네이버","한국","판교",
                "Django 백엔드 개발자",1000000,
                "Django 주니어 개발자를 채용합니다. 자격요건은..","Django");
        testUtil.createCompanyAndJobPosting("카카오","한국","판교",
                "Django 백엔드 개발자",500000,
                "Django 주니어 개발자를 채용합니다. 자격요건은..","Python");

        log.info("결과 {} ", jobPostingService.getJobPostingList());

        assertThat(jobPostingService.getJobPostingList().size()).isEqualTo(4);
        assertThat(jobPostingService.getJobPostingList().get(0).getLocation()).isEqualTo("서울");
        assertThat(jobPostingService.getJobPostingList().get(0).getCompanyName()).isEqualTo("원티드랩");
    }

    @Test
    @DisplayName("채용공고 검색: 원티드")
    void searchJobPosting() {
        testUtil.createCompanyAndJobPosting("삼성SDS","한국","서울",
                "백엔드 주니어 개발자",1500000,
                "백엔드 주니어 개발자를 채용합니다. 자격요건은..","Python");
        testUtil.createCompanyAndJobPosting("원티드코리아","한국","부산",
                "프론트엔드 개발자",500000,
                "프론트엔드 주니어 개발자를 채용합니다. 자격요건은..","javascript");
        testUtil.createCompanyAndJobPosting("네이버","한국","판교",
                "Django 백엔드 개발자",1000000,
                "Django 주니어 개발자를 채용합니다. 자격요건은..","Django");
        testUtil.createCompanyAndJobPosting("카카오","한국","판교",
                "Django 백엔드 개발자",500000,
                "Django 주니어 개발자를 채용합니다. 자격요건은..","Python");

        assertThat(jobPostingService.searchJobPosting("원티드").size()).isEqualTo(1);


    }

    @Test
    @DisplayName("채용공고 검색: Django")
    void searchJobPosting2() {
        testUtil.createCompanyAndJobPosting("원티드랩","한국","서울",
                "백엔드 주니어 개발자",1500000,
                "백엔드 주니어 개발자를 채용합니다. 자격요건은..","Python");
        testUtil.createCompanyAndJobPosting("원티드코리아","한국","부산",
                "프론트엔드 개발자",500000,
                "프론트엔드 주니어 개발자를 채용합니다. 자격요건은..","javascript");
        testUtil.createCompanyAndJobPosting("네이버","한국","판교",
                "Django 백엔드 개발자",1000000,
                "Django 주니어 개발자를 채용합니다. 자격요건은..","Django");
        testUtil.createCompanyAndJobPosting("카카오","한국","판교",
                "Django 백엔드 개발자",500000,
                "Django 주니어 개발자를 채용합니다. 자격요건은..","Python");

        assertThat(jobPostingService.searchJobPosting("Django").size()).isEqualTo(2);

    }

    @Test
    @DisplayName("채용공고 상세 페이지")
    void getJobPostingDetails() {

        Company company = testUtil.createCompany("원티드랩","한국","서울");

        JobPosting posting = testUtil.createJobPosting(company, "프론트엔드 개발자",500000,
                "프론트엔드 주니어 개발자를 채용합니다. 자격요건은..","javascript");
        testUtil.createJobPosting(company, "Django 백엔드 개발자",1000000,
                "Django 주니어 개발자를 채용합니다. 자격요건은..","Django");
        testUtil.createJobPosting(company, "백엔드 주니어 개발자",1500000,
                "백엔드 주니어 개발자를 채용합니다. 자격요건은..","Python");
        testUtil.createJobPosting(company, "Django 백엔드 개발자",500000,
                "Django 주니어 개발자를 채용합니다. 자격요건은..","Python");

        log.info("{} ", jobPostingService.getJobPostingDetails(posting.getId()));

    }
}