package com.wanted.pre_onboarding.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name="job_postings")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class JobPosting {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "job_posting_id", nullable = false)
    private Long id;
    @Column(name = "position", nullable = false)
    @NotBlank
    @Size(max = 50)
    private String position;
    @Column
    private int compensation;
    @Column(name = "description", nullable = false)
    @Size(max = 1000)
    private String description;
    @Column(name = "skill", nullable = false)
    @Size(max = 30)
    private String skill;

    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "company_id")
    private Company company;

    @OneToMany(mappedBy = "jobPosting")
    @Builder.Default
    @ToString.Exclude
    private List<JobPostingUser> postingUser = new ArrayList<>();

    public void update(String position, int compensation, String description, String skill) {
        this.position = position;
        this.compensation = compensation;
        this.description = description;
        this.skill = skill;

    }

}
