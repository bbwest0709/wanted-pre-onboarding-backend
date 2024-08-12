package com.wanted.wantedpreonboardingbackend.domain.post.dto.request;

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

}
