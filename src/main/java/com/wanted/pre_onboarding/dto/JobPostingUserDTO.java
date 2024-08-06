package com.wanted.pre_onboarding.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JobPostingUserDTO {
    private Long jobPostingUserId;
    @NotNull
    private UUID userId;
    @NotNull
    private Long jobPostingId;
}
