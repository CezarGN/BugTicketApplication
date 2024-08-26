package org.example.springskeleton.developer;

import org.example.springskeleton.developer.seniority.ESeniority;
import org.example.springskeleton.developer.seniority.Seniority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SeniorityRepository extends JpaRepository<Seniority, Long> {

    Optional<Seniority> findBySeniority(ESeniority seniority);
}
