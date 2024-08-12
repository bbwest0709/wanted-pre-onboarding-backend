package com.wanted.wantedpreonboardingbackend.domain.user.repository;

import com.wanted.wantedpreonboardingbackend.domain.user.entity.Apply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ApplyRepository extends JpaRepository<Apply, Long> {

    @Query("SELECT COUNT(a) > 0 " +
            "FROM Apply a " +
            "WHERE a.user.id = :id AND a.jobPosting.jobPostId = :jobPostId")
    boolean existsByUserIdAndJobPostId(@Param("id") Long id, @Param("jobPostId") Long jobPostId);

}
