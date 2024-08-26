package org.example.springskeleton.project;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    @Query(value = "SELECT * FROM project WHERE project.name LIKE %:name%", nativeQuery = true)
    Page<Project> findProjectByNameContaining(String name, Pageable pageable);
}
