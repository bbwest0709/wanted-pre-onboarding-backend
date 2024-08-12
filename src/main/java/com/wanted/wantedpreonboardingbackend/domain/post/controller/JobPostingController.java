package com.wanted.wantedpreonboardingbackend.domain.post.controller;

import com.wanted.wantedpreonboardingbackend.domain.post.dto.request.JobPostingSaveRequestDTO;
import com.wanted.wantedpreonboardingbackend.domain.post.dto.request.JobPostingUpdateRequestDTO;
import com.wanted.wantedpreonboardingbackend.domain.post.dto.response.JobPostDetailResponseDTO;
import com.wanted.wantedpreonboardingbackend.domain.post.dto.response.JobPostingResponseDTO;
import com.wanted.wantedpreonboardingbackend.domain.post.service.JobPostingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class JobPostingController {

    private final JobPostingService jobPostingService;

    /**
     * 채용공고 등록
     */
    @PostMapping
    public void save(@RequestBody JobPostingSaveRequestDTO request) {
        jobPostingService.save(request);
    }

    /**
     * 채용공고 수정
     *
     * @param jobPostId
     * @param request
     */
    @PatchMapping("/update/{jobPostId}")
    public void update(@PathVariable("jobPostId") Long jobPostId, @RequestBody JobPostingUpdateRequestDTO request) {
        jobPostingService.update(jobPostId, request);
    }

    /**
     * 채용공고 삭제
     *
     * @param jobPostId
     */
    @DeleteMapping("/delete/{jobPostId}")
    public void delete(@PathVariable("jobPostId") Long jobPostId) {
        jobPostingService.delete(jobPostId);
    }

    /**
     * 채용공고 조회
     *
     * @return
     */
    @GetMapping("/list")
    public List<JobPostingResponseDTO> getAllJobPostings() {
        return jobPostingService.list();
    }

    /**
     * 채용공고 검색
     * 회사명, 포지션, 사용기술 검색 가능
     */
    @GetMapping("/search")
    public List<JobPostingResponseDTO> search(@RequestParam("search") String search) {
        return jobPostingService.searchJobPostings(search);
    }

    /**
     * 채용 상세
     */
    @GetMapping("/list/{jobPostId}")
    public JobPostDetailResponseDTO getPostDetail(@PathVariable("jobPostId") Long jobPostId) {
        return jobPostingService.getPostDetail(jobPostId);
    }

}
