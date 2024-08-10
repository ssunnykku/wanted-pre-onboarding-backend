package com.wanted.pre_onboarding.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Getter
@Setter
@Table(name="job_postings_users")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobPostingUser {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "posting_user_id", nullable = false)
    private Long postingUserId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotNull
    private User user;

    @ManyToOne
    @JoinColumn(name = "job_posting_id")
    @NotNull
    private JobPosting jobPosting;
}
