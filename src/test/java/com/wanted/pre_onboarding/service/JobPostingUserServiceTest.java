package com.wanted.pre_onboarding.service;

import com.wanted.pre_onboarding.util.TestSetUp;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
@ActiveProfiles("test")
class JobPostingUserServiceTest extends TestSetUp {
    @Autowired
    JobPostingUserService jobPostingUserService;

    @Test
    @Transactional
    @DisplayName("채용 공고 지원")
    void applyJopPosting() {
        jobPostingUserService.applyJopPosting(userId, jobPostingId);

        log.info("결과 {} ",jobPostingUserRepository.findAll());
        assertThat(jobPostingUserRepository.findAll().size()).isEqualTo(1);
        assertThat(jobPostingUserRepository.findAll().get(0).getJobPosting().getId()).isEqualTo(jobPostingId);
        assertThat(jobPostingUserRepository.findAll().get(0).getUser().getUserId()).isEqualTo(userId);

    }
}