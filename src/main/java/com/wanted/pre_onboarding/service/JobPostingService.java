package com.wanted.pre_onboarding.service;

import com.wanted.pre_onboarding.domain.Company;
import com.wanted.pre_onboarding.domain.JobPosting;
import com.wanted.pre_onboarding.dto.JobPostingDTO;
import com.wanted.pre_onboarding.repository.CompanyRepository;
import com.wanted.pre_onboarding.repository.JobPostingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JobPostingService {
    private final JobPostingRepository jobPostingRepository;
    private final CompanyRepository companyRepository;
    /* 채용공고 등록 */
    public void addJobPosting(JobPostingDTO jobPostingDTO){
        JobPosting jobPosting = JobPosting.builder()
                .position(jobPostingDTO.getPosition())
                .compensation(jobPostingDTO.getCompensation())
                .description(jobPostingDTO.getDescription())
                .skill(jobPostingDTO.getSkill())
                .build();

        Company company = companyRepository.findById(jobPostingDTO.getCompanyId())
                .orElseThrow(() -> new RuntimeException("Company not found"));

        jobPosting.setCompany(company);

       jobPostingRepository.save(jobPosting);
    }

    /* 채용공고 수정 */
    public void editJobPosting(Long jobPostingId, JobPostingDTO jobPostingDTO){

        jobPostingRepository.findById(jobPostingId).ifPresent(post -> {
            JobPosting data =  jobPostingRepository.findById(jobPostingId).get();
            data.update(jobPostingDTO.getPosition(), jobPostingDTO.getCompensation(), jobPostingDTO.getDescription(), jobPostingDTO.getSkill());

            jobPostingRepository.save(data);
        });
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

        Optional<JobPosting> getPosting = jobPostingRepository.findById(jobPostingId);

        JobPostingDTO result = null;

        if(getPosting.isPresent()) {
            List<JobPosting> getOtherPosting = jobPostingRepository.findAllByCompanyId(getPosting.get().getCompany().getId());
            List<Long> otherPosting = new ArrayList<>();

            for(JobPosting posting : getOtherPosting) {
                if(posting.getId() != getPosting.get().getId()) {
                    otherPosting.add(posting.getId());
                }
            }

            JobPosting post = getPosting.get();
            result = JobPostingDTO.builder()
                    .jobPostingId(post.getId())
                    .position(post.getPosition())
                    .compensation(post.getCompensation())
                    .description(post.getDescription())
                    .skill(post.getSkill())
                    .companyId(post.getCompany().getId())
                    .companyName(post.getCompany().getCompanyName())
                    .country(post.getCompany().getCountry())
                    .location(post.getCompany().getLocation())
                    .jobPostingIdList(otherPosting)
                    .build();
        }

        return result;

    }
}
