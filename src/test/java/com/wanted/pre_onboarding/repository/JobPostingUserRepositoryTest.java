package com.wanted.pre_onboarding.repository;

import com.wanted.pre_onboarding.domain.JobPosting;
import com.wanted.pre_onboarding.domain.JobPostingUser;
import com.wanted.pre_onboarding.domain.User;
import com.wanted.pre_onboarding.util.TestSetUp;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Slf4j
@ActiveProfiles("test")
class JobPostingUserRepositoryTest extends TestSetUp {

    @Test
    @DisplayName("채용 공고 지원")
    @Transactional
    void apply() {
        User user = null;
        JobPosting jobPosting = null;
        if (userRepository.findById(userId).isPresent() && jobPostingRepository.findById(jobPostingId).isPresent()){

            user = userRepository.findById(userId).get();
            jobPosting = jobPostingRepository.findById(jobPostingId).get();

            JobPostingUser jobPostingUser = JobPostingUser.builder()
                    .user(user)
                    .jobPosting(jobPosting)
                    .build();

            jobPostingUserRepository.save(jobPostingUser);

            assertThat(jobPostingUserRepository.findById(jobPostingUser.getPostingUserId())).isNotNull();

        }

    }
}