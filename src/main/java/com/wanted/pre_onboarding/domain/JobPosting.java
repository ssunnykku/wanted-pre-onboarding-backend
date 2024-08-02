package com.wanted.pre_onboarding.domain;

import jakarta.persistence.*;
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
    private String position;
    @Column
    private int compensation;
    @Column(name = "description", nullable = false)
    private String description;
    @Column(name = "skill", nullable = false)
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
