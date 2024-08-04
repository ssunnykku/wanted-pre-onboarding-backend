package com.wanted.pre_onboarding.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name="companies")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Company {
    @Id @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name="uuid2", strategy = "uuid2")
    @Column(name="company_id")
    private UUID id;
    @Column(name = "company_name", nullable = false)
    private String companyName;
    @Column(name = "location", nullable = false)
    private String location;
    @Column(name = "country", nullable = false)
    private String country;
    @OneToMany(mappedBy = "company")
    @Builder.Default
    @ToString.Exclude
    private List<JobPosting> postings = new ArrayList<>();
}
