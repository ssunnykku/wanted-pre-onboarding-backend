package com.wanted.pre_onboarding.repository;

import com.wanted.pre_onboarding.domain.Company;
import com.wanted.pre_onboarding.domain.JobPosting;
import com.wanted.pre_onboarding.domain.JobPostingUser;
import com.wanted.pre_onboarding.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
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

            JobPostingUser jobPostingUser = JobPostingUser.builder().user(user).jobPosting(jobPosting).build();
            postingUserRepository.save(jobPostingUser);

            assertThat(postingUserRepository.findById(jobPostingUser.getPostingUserId())).isNotNull();

        }

    }
}