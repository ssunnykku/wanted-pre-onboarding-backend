package com.wanted.pre_onboarding.service;

import com.wanted.pre_onboarding.domain.Company;
import com.wanted.pre_onboarding.domain.JobPosting;
import com.wanted.pre_onboarding.dto.JobPostingDTO;
import com.wanted.pre_onboarding.dto.JobPostingUpdateDTO;
import com.wanted.pre_onboarding.repository.CompanyRepository;
import com.wanted.pre_onboarding.repository.JobPostingRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JobPostingService {
    private final JobPostingRepository jobPostingRepository;
    private final CompanyRepository companyRepository;
    /* 채용공고 등록 */
    public Long addJobPosting(JobPostingDTO jobPostingDTO){

            if (jobPostingDTO.getPosition() == null || jobPostingDTO.getPosition() == "" ||
                    jobPostingDTO.getSkill() == null || jobPostingDTO.getSkill() == "") {
                throw new IllegalArgumentException("required input value");
            }

            Company company = companyRepository.findById(jobPostingDTO.getCompanyId())
                    .orElseThrow(() -> new EntityNotFoundException("Company not found with id: " + jobPostingDTO.getCompanyId()));

            JobPosting jobPosting = JobPosting.builder()
                    .position(jobPostingDTO.getPosition())
                    .compensation(jobPostingDTO.getCompensation())
                    .description(jobPostingDTO.getDescription())
                    .skill(jobPostingDTO.getSkill())
                    .company(company)
                    .build();

            jobPostingRepository.save(jobPosting);

            return jobPosting.getId();

    }

    /* 채용공고 수정 */
    public void editJobPosting(Long jobPostingId, JobPostingUpdateDTO jobPostingUpdateDTO){

        JobPosting jobPosting = jobPostingRepository.findById(jobPostingId)
                .orElseThrow(() -> new EntityNotFoundException("Job posting not found with id " + jobPostingId));

        jobPosting.update(jobPostingUpdateDTO.getPosition(),
                jobPostingUpdateDTO.getCompensation(),
                jobPostingUpdateDTO.getDescription(),
                jobPostingUpdateDTO.getSkill());

        jobPostingRepository.save(jobPosting);

    }

    /* 채용공고 삭제 */
    public void removeJobPosting(Long jobPostingId){
        jobPostingRepository.deleteById(jobPostingId);

    }

    /* 채용공고 목록 */
    public List<JobPostingDTO> getJobPostingList(){
        List<JobPosting> postingList = jobPostingRepository.findAllWithCompany();

        List<JobPostingDTO> result = new ArrayList<>();

        postingList.forEach(posting -> {
            result.add(JobPostingDTO.builder().jobPostingId(posting.getId())
                    .position(posting.getPosition())
                    .compensation(posting.getCompensation())
                    .description(posting.getDescription())
                    .skill(posting.getSkill())
                    .companyId(posting.getCompany().getId())
                    .companyName(posting.getCompany().getCompanyName())
                    .country(posting.getCompany().getCountry())
                    .location(posting.getCompany().getLocation()).build());
        });
        return result;
    }

    /* 채용공고 검색 */
    public List<JobPostingDTO> searchJobPosting(String keyword){
        List<JobPosting> postingList = jobPostingRepository.search(keyword);

        List<JobPostingDTO> result = new ArrayList<>();

        postingList.forEach(posting -> {
            result.add(JobPostingDTO.builder()
                    .jobPostingId(posting.getId())
                    .position(posting.getPosition())
                    .compensation(posting.getCompensation())
                    .description(posting.getDescription())
                    .skill(posting.getSkill())
                    .companyId(posting.getCompany().getId())
                    .companyName(posting.getCompany().getCompanyName())
                    .country(posting.getCompany().getCountry())
                    .location(posting.getCompany().getLocation()).build());
        });

        return result;
    }

    /* 채용공고 상세 페이지 */
    public JobPostingDTO getJobPostingDetails(Long jobPostingId){

        JobPosting getPosting = jobPostingRepository.findById(jobPostingId)
                .orElseThrow(() -> new EntityNotFoundException("Job posting not found with id " + jobPostingId));;

        JobPostingDTO result = null;


            List<JobPosting> getOtherPosting = jobPostingRepository.findAllByCompanyId(getPosting.getCompany().getId());
            List<Long> otherPosting = new ArrayList<>();

            for(JobPosting posting : getOtherPosting) {
                if(posting.getId() != getPosting.getId()) {
                    otherPosting.add(posting.getId());
                }
            }

            result = JobPostingDTO.builder()
                    .jobPostingId(getPosting.getId())
                    .position(getPosting.getPosition())
                    .compensation(getPosting.getCompensation())
                    .description(getPosting.getDescription())
                    .skill(getPosting.getSkill())
                    .companyId(getPosting.getCompany().getId())
                    .companyName(getPosting.getCompany().getCompanyName())
                    .country(getPosting.getCompany().getCountry())
                    .location(getPosting.getCompany().getLocation())
                    .jobPostingIdList(otherPosting)
                    .build();


        return result;

    }
}
