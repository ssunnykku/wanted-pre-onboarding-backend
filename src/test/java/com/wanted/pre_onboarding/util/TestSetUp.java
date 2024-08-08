package com.wanted.pre_onboarding.util;

import com.wanted.pre_onboarding.domain.Company;
import com.wanted.pre_onboarding.domain.JobPosting;
import com.wanted.pre_onboarding.domain.User;
import com.wanted.pre_onboarding.repository.CompanyRepository;
import com.wanted.pre_onboarding.repository.JobPostingRepository;
import com.wanted.pre_onboarding.repository.JobPostingUserRepository;
import com.wanted.pre_onboarding.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestSetUp {
    @Autowired
    protected JobPostingUserRepository jobPostingUserRepository;

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected JobPostingRepository jobPostingRepository;

    @Autowired
    protected CompanyRepository companyRepository;


    @Autowired
    private TestUtil testUtil;


    protected Long jobPostingId;
    protected UUID userId;
    protected UUID companyId;

    @BeforeEach
    public void setUp() {
        jobPostingUserRepository.deleteAll();
        jobPostingRepository.deleteAll();
        companyRepository.deleteAll();
        userRepository.deleteAll();

        Company c1 = createCompany("원티드랩", "한국", "서울");

        User user = User.builder().email("sun@gmail.com").password("1234").name("김선희").build();
        userRepository.save(user);

        JobPosting posting = createJobPosting(c1, "백엔드 주니어 개발자", 1000000, "원티드랩에서 백엔드 주니어 개발자를 채용합니다. 자격요건은..", "Python");

        createCompanyAndJobPosting("원티드코리아","한국","부산","프론트엔드 개발자",500000,"프론트엔드 주니어 개발자를 채용합니다. 자격요건은..","javascript");
        createCompanyAndJobPosting("네이버","한국","판교","Django 백엔드 개발자",1000000,"Django 주니어 개발자를 채용합니다. 자격요건은..","Django");
        createCompanyAndJobPosting("카카오","한국","판교","Django 백엔드 개발자",500000,"Django 주니어 개발자를 채용합니다. 자격요건은..","Python");

        jobPostingId = posting.getId();
        userId = user.getUserId();
        companyId = c1.getId();

    }

    @AfterEach
    public void afterEach() {
        jobPostingUserRepository.deleteAll();
        jobPostingRepository.deleteAll();
        companyRepository.deleteAll();
        userRepository.deleteAll();
    }

    protected Company createCompany(String companyName, String country, String location) {
        return testUtil.createCompany(companyName, country, location);
    }

    protected JobPosting createJobPosting(Company company, String position, int compensation, String description, String skill) {
        return testUtil.createJobPosting(company, position, compensation, description, skill);
    }

    protected void createCompanyAndJobPosting(String companyName, String country, String location, String position, int compensation, String description, String skill){
        testUtil.createCompanyAndJobPosting(companyName, country, location, position, compensation, description, skill);

    }


}
