package com.wanted.wantedpreonboardingbackend.domain.post.dto.request;

import com.wanted.wantedpreonboardingbackend.domain.post.entity.JobPosting;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class JobPostingUpdateRequestDTO {
    private Long companyId;
    private String position;
    private Integer reward;
    private String description;
    private String technologies;

    @Builder
    public JobPostingUpdateRequestDTO(Long companyId, String position, Integer reward, String description, String technologies) {
        this.companyId = companyId;
        this.position = position;
        this.reward = reward;
        this.description = description;
        this.technologies = technologies;
    }

    public JobPosting toEntity() {
        return JobPosting.builder()
                .position(this.position)
                .reward(this.reward)
                .description(this.description)
                .technologies(this.technologies)
                .build();
    }


}
