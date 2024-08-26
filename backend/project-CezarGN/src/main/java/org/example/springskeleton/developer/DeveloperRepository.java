package org.example.springskeleton.developer;


import org.example.springskeleton.developer.seniority.ESeniority;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface DeveloperRepository extends JpaRepository<Developer, Long> {
    @Query(value = "SELECT * FROM developer where developer.project_id IS NULL", nativeQuery = true)
    Page<Developer> getIdleDevelopers(Pageable pageable);
    @Query(value = "SELECT * FROM developer where developer.project_id is NULL OR developer.project_id = ?", nativeQuery = true)
    Page<Developer> getDevelopersOnProjectAndIdle(Pageable pageable, Long id);
    @Query(value = "SELECT * FROM developer where developer.project_id = ?", nativeQuery = true)
    List<Developer> getDeveloperByProjectId(Long id);
    Optional<Developer> findByAppUser_Id(Long id);
    @Query("SELECT d FROM Developer d JOIN d.appUser a WHERE " +
            "(a.username LIKE %:username%) AND " +
            "(:seniority IS NULL OR d.seniority.seniority = :seniority)")
    Page<Developer> findByUsernameContainingAndSeniority(@Param("username") String username,
                                                         @Param("seniority") ESeniority seniority,
                                                         Pageable pageable);



}
