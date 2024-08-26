package org.example.springskeleton.bug;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.example.springskeleton.developer.Developer;
import org.example.springskeleton.project.Project;

@Entity
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class Bug {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "description", nullable = false, length = 500)
    private String description;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Developer developer;

    @ManyToOne(cascade = CascadeType.REMOVE)
    private Project project;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private BugStatus status;
}
