package com.wanted.pre_onboarding.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wanted.pre_onboarding.domain.JobPosting;

import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.UUID;

import static com.wanted.pre_onboarding.domain.QCompany.company;
import static com.wanted.pre_onboarding.domain.QJobPosting.jobPosting;

@Repository
public class CompanyPostingRepositoryImpl implements CompanyPostingRepository{
    private final JPAQueryFactory jpaQueryFactory;

    public CompanyPostingRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }


    @Override
    public List<JobPosting> findAllByCompanyId(UUID companyId) {

        return jpaQueryFactory.selectFrom(jobPosting)
                .where(jobPosting.company.id.eq(companyId))
                .fetch();
    }

    @Override
    public List<JobPosting> search(String keyword) {

        return jpaQueryFactory.selectFrom(jobPosting)
                .where(jobPosting.position.contains(keyword)
                        .or(jobPosting.description.contains(keyword))
                        .or(jobPosting.skill.contains(keyword))
                        .or(jobPosting.company.companyName.contains(keyword))
                        .or(jobPosting.company.companyName.contains(keyword))
                        .or(jobPosting.company.country.contains(keyword))
                        .or(jobPosting.company.location.contains(keyword)))
                .fetch();
    }

    @Override
    public List<JobPosting> findAllWithCompany() {

        return jpaQueryFactory.selectFrom(jobPosting).join(jobPosting.company, company).fetch();
    }


}
