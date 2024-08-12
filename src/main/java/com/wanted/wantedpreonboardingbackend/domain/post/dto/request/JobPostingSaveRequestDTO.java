package com.wanted.wantedpreonboardingbackend.domain.post.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JobPostingSaveRequestDTO {

    private Long companyId;
    private String position;
    private Integer reward;
    private String description;
    private String technologies;

}
