package com.wanted.pre_onboarding.service;

import com.wanted.pre_onboarding.domain.JobPosting;
import com.wanted.pre_onboarding.domain.JobPostingUser;
import com.wanted.pre_onboarding.domain.User;
import com.wanted.pre_onboarding.repository.JobPostingRepository;
import com.wanted.pre_onboarding.repository.JobPostingUserRepository;
import com.wanted.pre_onboarding.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class JobPostingUserService {

    private final JobPostingUserRepository jobPostingUserRepository;
    private final UserRepository userRepository;
    private final JobPostingRepository jobPostingRepository;

    /* 채용공고 지원*/
    public void applyJopPosting(UUID userId, Long jobPostingId) {

        User user = userRepository.findById(userId).get();
        JobPosting jobPosting = jobPostingRepository.findById(jobPostingId).get();

        JobPostingUser jobPostingUser = JobPostingUser.builder()
                .user(user)
                .jobPosting(jobPosting)
                .build();

        jobPostingUserRepository.save(jobPostingUser);
    }

}
