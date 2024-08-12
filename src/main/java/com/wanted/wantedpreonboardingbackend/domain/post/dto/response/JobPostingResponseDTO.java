package com.wanted.wantedpreonboardingbackend.domain.post.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class JobPostingResponseDTO {

    private Long jobPostId;
    private String companyName;
    private String country;
    private String location;
    private String position;
    private Integer reward;
    private String technologies;

}
