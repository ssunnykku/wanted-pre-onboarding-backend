package com.wanted.pre_onboarding.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @NotNull
    private UUID companyId;
    private String companyName;
    private String country;
    private String location;
    @NotBlank
    @Size(max = 50)
    private String position;
    @NotNull
    private int compensation;
    @NotBlank
    @Size(max = 30)
    private String skill;
    @Size(max = 1000)
    private String description;
    private List<Long> jobPostingIdList;

}
