package com.wanted.pre_onboarding.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Builder
@Entity
@Getter
@Setter
@Table(name="Users")
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name="uuid2", strategy = "uuid2")
    @Column
    private UUID userId;
    @Column(name = "email", nullable = false)
    @Email
    private String email;
    @Column(name = "password", nullable = false)
    @NotBlank
    private String password;
    @Column(name = "name", nullable = false)
    @NotBlank
    @Size(min = 2, max = 50)
    private String name;

    @OneToMany(mappedBy = "user")
    @Builder.Default
    @NotNull
    private List<JobPostingUser> postingUser = new ArrayList<>();

}
