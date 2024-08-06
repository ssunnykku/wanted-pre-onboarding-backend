package com.wanted.pre_onboarding.controller;

import com.wanted.pre_onboarding.dto.JobPostingUserDTO;
import com.wanted.pre_onboarding.service.JobPostingUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/job-postings")
@Slf4j
public class JobPostingUserController {
    private final JobPostingUserService jobPostingUserService;

    /* 채용공고 지원*/
    @PostMapping("/users")
    public ResponseEntity<String> applyJobPosting(@Valid @RequestBody JobPostingUserDTO jobPostingUserDTO){
        jobPostingUserService.applyJopPosting(jobPostingUserDTO.getUserId(), jobPostingUserDTO.getJobPostingId());
        return ResponseEntity.status(HttpStatus.CREATED).body("success");
    }

}
