package com.wanted.wantedpreonboardingbackend.domain.post.entity;

import com.wanted.wantedpreonboardingbackend.domain.common.entity.BaseTime;
import com.wanted.wantedpreonboardingbackend.domain.company.entity.Company;
import jakarta.persistence.*;
import lombok.*;

import java.util.Optional;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "job_post")
public class JobPosting extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long jobPostId;

    @Column(nullable = false)
    private String position;

    @Column(nullable = false)
    private Integer reward;

    @Column(length = 10000, nullable = false)
    private String description;

    @Column(nullable = false)
    private String technologies;

    @ManyToOne
    @JoinColumn(name = "companyId", updatable = false)
    private Company company;

    @Builder
    public JobPosting(Long jobPostId, Company company, String position, Integer reward, String description, String technologies) {
        this.jobPostId = jobPostId;
        this.company = company;
        this.position = position;
        this.reward = reward;
        this.description = description;
        this.technologies = technologies;
    }

}
