package com.wanted.pre_onboarding.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JobPostingDTO {
    private Long jobPostingId;
    @NotNull(message = "Company ID is required")
    private UUID companyId;
    private String companyName;
    private String country;
    private String location;
    @NotBlank(message = "Position is required")
    @Size(max = 50)
    private String position;
    @Range(min = 100000, max = 2000000)
    private int compensation;
    @NotBlank(message = "Skill is required")
    @Size(max = 30)
    private String skill;
    @Size(max = 1000)
    private String description;
    private List<Long> jobPostingIdList;

}
