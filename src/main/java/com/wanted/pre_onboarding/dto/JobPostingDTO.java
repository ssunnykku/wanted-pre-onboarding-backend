package com.wanted.pre_onboarding.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JobPostingDTO {
    private Long jobPostingId;
    private UUID companyId;
    private String companyName;
    private String country;
    private String location;
    private String position;
    private int compensation;
    private String skill;
    private String description;
    private List<Long> jobPostingIdList;

}
