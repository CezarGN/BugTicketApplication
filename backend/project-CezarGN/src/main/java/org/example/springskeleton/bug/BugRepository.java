package org.example.springskeleton.bug;

import org.example.springskeleton.bug.Bug;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BugRepository extends JpaRepository<Bug, Long> {

    @Query(value = "SELECT * FROM bug where bug.developer_id = ?1 AND bug.project_id = ?2", nativeQuery = true)
    Page<Bug> findByDeveloper_IdAndProject_Id(Long developerId, Long projectId, Pageable pageable);

    Page<Bug> findByProject_Id(Long id, Pageable pageable);
}
