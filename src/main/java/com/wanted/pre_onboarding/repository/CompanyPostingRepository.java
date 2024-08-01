package com.wanted.pre_onboarding.repository;

import com.wanted.pre_onboarding.domain.JobPosting;

import java.util.List;
import java.util.UUID;

public interface CompanyPostingRepository {

    List<JobPosting> findAllByCompanyId(UUID companyId);

    List<JobPosting> search(String keyword);

    List<JobPosting> findAllWithCompany();
}
