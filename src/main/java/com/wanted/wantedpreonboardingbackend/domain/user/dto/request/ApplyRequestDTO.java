package com.wanted.wantedpreonboardingbackend.domain.user.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ApplyRequestDTO {

    private Long id;
    private Long jobPostId;

    @Builder
    public ApplyRequestDTO(Long id, Long jobPostId) {
        this.id = id;
        this.jobPostId = jobPostId;
    }

}
