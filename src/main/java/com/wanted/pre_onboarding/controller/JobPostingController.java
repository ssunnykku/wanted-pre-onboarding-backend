package com.wanted.pre_onboarding.controller;

import com.wanted.pre_onboarding.dto.JobPostingDTO;
import com.wanted.pre_onboarding.service.JobPostingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/job-postings")
@Slf4j
public class JobPostingController {
    private final JobPostingService jobPostingService;
    /* 채용공고 등록 */
    @PostMapping
    public ResponseEntity<String> addJobPosting(@RequestBody JobPostingDTO jobPostingDTO){
        jobPostingService.addJobPosting(jobPostingDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("success");
    }
    /* 채용공고 수정 */
    @PutMapping("/{jobPostingId}")
    public ResponseEntity<String> editJobPosting(@PathVariable Long jobPostingId, @RequestBody JobPostingDTO jobPostingDTO) {
        jobPostingService.editJobPosting(jobPostingId, jobPostingDTO);
        return ResponseEntity.status(HttpStatus.OK).body("success");
    }
    /* 채용공고 삭제 */
    @DeleteMapping("/{jobPostingId}")
    public ResponseEntity<String> removeJobPosting(@PathVariable Long jobPostingId){
        jobPostingService.removeJobPosting(jobPostingId);
        return ResponseEntity.status(HttpStatus.OK).body("success");
    }
    /* 채용공고 목록 */
    @GetMapping
    public ResponseEntity<List<JobPostingDTO>> getJobPostingList(){
       return ResponseEntity.status(HttpStatus.OK).body(jobPostingService.getJobPostingList());
    }
    /* 채용공고 검색 */
    @GetMapping("/search")
    public ResponseEntity<List<JobPostingDTO>> searchJobPosting(@RequestParam String keyword){
        return ResponseEntity.status(HttpStatus.OK).body(jobPostingService.searchJobPosting(keyword));

    }
    /* 채용공고 상세 페이지 */
    @GetMapping("/{jobPostingId}")
    public ResponseEntity<JobPostingDTO> getJobPostingDetails(@PathVariable Long jobPostingId){
        return ResponseEntity.status(HttpStatus.OK).body(jobPostingService.getJobPostingDetails(jobPostingId));
    }

}
