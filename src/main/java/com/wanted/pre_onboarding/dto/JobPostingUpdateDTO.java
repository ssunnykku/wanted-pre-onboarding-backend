package com.wanted.pre_onboarding.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JobPostingUpdateDTO {
    @NotNull
    private Long jobPostingId;
    @NotBlank
    @Size(max = 50)
    private String position;
    @NotNull
    @Range(min = 100000, max = 2000000)
    private int compensation;
    @NotBlank
    @Size(max = 30)
    private String skill;
    @Size(max = 1000)
    private String description;

}
