package org.example.springskeleton.bug;

import org.example.springskeleton.bug.BugStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BugStatusRepository extends JpaRepository<BugStatus, Long> {
    Optional<BugStatus> findByStatus(EStatus status);
}
