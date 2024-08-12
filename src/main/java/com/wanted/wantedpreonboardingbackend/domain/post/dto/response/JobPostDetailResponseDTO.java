package com.wanted.wantedpreonboardingbackend.domain.post.dto.response;

import com.wanted.wantedpreonboardingbackend.domain.post.entity.JobPosting;
import lombok.*;

import java.util.List;

@Getter
@ToString
@AllArgsConstructor
public class JobPostDetailResponseDTO {

    private Long jobPostId;
    private String companyName;
    private String country;
    private String location;
    private String position;
    private String description;
    private Integer reward;
    private String technologies;
    private List<Long> otherJobPostIds;

}
