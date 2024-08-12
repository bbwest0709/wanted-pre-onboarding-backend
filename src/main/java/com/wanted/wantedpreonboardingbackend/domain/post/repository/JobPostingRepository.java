package com.wanted.wantedpreonboardingbackend.domain.post.repository;

import com.wanted.wantedpreonboardingbackend.domain.post.entity.JobPosting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JobPostingRepository extends JpaRepository<JobPosting, Long> {

    @Query("SELECT CASE WHEN COUNT(jp) > 0 THEN true ELSE false END " +
            "FROM JobPosting jp WHERE jp.jobPostId = :jobPostId AND jp.company.companyId = :companyId")
    boolean existsByIdAndCompanyId(@Param("jobPostId") Long jobPostId,
                                   @Param("companyId") Long companyId);

    @Query("SELECT jp FROM JobPosting jp " +
            "WHERE LOWER(jp.position) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "OR LOWER(jp.company.companyName) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "OR LOWER(jp.technologies) LIKE LOWER(CONCAT('%', :search, '%'))")
    List<JobPosting> searchByKeyword(@Param("search") String search);

    @Query("SELECT jp FROM JobPosting jp WHERE jp.company.id = :companyId")
    List<JobPosting> findByCompanyId(Long companyId);

}
