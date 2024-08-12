package com.wanted.wantedpreonboardingbackend.domain.user.entity;

import com.wanted.wantedpreonboardingbackend.domain.post.entity.JobPosting;
import jakarta.persistence.*;
import lombok.*;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "apply")
public class Apply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long applyId;

    @ManyToOne
    @JoinColumn(name = "id", updatable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "jobPostId", updatable = false)
    private JobPosting jobPosting;

    @Builder
    public Apply(Long applyId, User user, JobPosting jobPosting) {
        this.applyId = applyId;
        this.user = user;
        this.jobPosting = jobPosting;
    }

}
