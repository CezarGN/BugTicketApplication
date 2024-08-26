package org.example.springskeleton.developer;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.example.springskeleton.developer.seniority.Seniority;
import org.example.springskeleton.project.Project;
import org.example.springskeleton.auth.user.AppUser;

@Entity
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class Developer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private AppUser appUser;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private Seniority seniority;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "project_id")
    private Project project;
}
