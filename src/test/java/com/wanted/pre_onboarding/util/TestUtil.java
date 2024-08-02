package com.wanted.pre_onboarding.util;

import com.wanted.pre_onboarding.domain.Company;
import com.wanted.pre_onboarding.domain.JobPosting;
import com.wanted.pre_onboarding.repository.CompanyRepository;
import com.wanted.pre_onboarding.repository.JobPostingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class TestUtil {
    @Autowired
    private JobPostingRepository jobPostingRepository;
    @Autowired
    private CompanyRepository companyRepository;

    public Company createCompany(String companyName, String country, String location) {
        Company company = Company.builder()
                .companyName(companyName)
                .country(country)
                .location(location)
                .build();
        companyRepository.save(company);
        return company;
    }

    public JobPosting createJobPosting(Company company, String position, int compensation, String description, String skill) {
        JobPosting posting = JobPosting.builder()
                .position(position)
                .compensation(compensation)
                .description(description)
                .skill(skill)
                .company(company)
                .build();
        jobPostingRepository.save(posting);
        return posting;
    }


    public void createCompanyAndJobPosting(String companyName, String country, String location, String position, int compensation, String description, String skill){

        Company company = createCompany(companyName,country,location);
        JobPosting posting = createJobPosting(company,position,compensation,description,skill);

    }

}
