package com.wanted.pre_onboarding.repository;

import com.wanted.pre_onboarding.domain.Company;
import com.wanted.pre_onboarding.domain.JobPosting;
import com.wanted.pre_onboarding.domain.User;
import jakarta.annotation.PostConstruct;
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
    protected JobPostingUserRepository postingUserRepository;

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected JobPostingRepository jobPostingRepository;

    @Autowired
    protected CompanyRepository companyRepository;

    protected Long jobPostingId;
    protected UUID userId;
    protected UUID companyId;

    @Transactional
    @PostConstruct
    public void setUp() {
        jobPostingRepository.deleteAll();

        Company c1 = createCompany("원티드", "한국", "서울");

        User user = User.builder().email("sun@gmail.com").password("1234").name("김선희").build();
        userRepository.save(user);

        JobPosting posting = createJobPosting(c1, "백엔드 주니어 개발자", 1000000, "원티드랩에서 백엔드 주니어 개발자를 채용합니다. 자격요건은..", "Python");
        createJobPosting(c1, "백엔드 주니어 개발자2", 1000000, "원티드랩에서 백엔드 주니어 개발자를 채용합니다. 자격요건은..", "Python");

        jobPostingId = posting.getId();
        userId = user.getUserId();
        companyId = c1.getId();

    }

    protected Company createCompany(String companyName, String country, String location) {
        Company company = Company.builder()
                .companyName(companyName)
                .country(country)
                .location(location)
                .build();
        companyRepository.save(company);
        return company;
    }

    protected JobPosting createJobPosting(Company company, String position, int compensation, String description, String technology) {
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



}
