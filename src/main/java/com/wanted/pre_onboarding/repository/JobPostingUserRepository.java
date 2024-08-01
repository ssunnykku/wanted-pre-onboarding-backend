package com.wanted.pre_onboarding.repository;

import com.wanted.pre_onboarding.domain.JobPostingUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobPostingUserRepository extends JpaRepository<JobPostingUser, Long> {
}
