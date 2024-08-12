package com.wanted.wantedpreonboardingbackend.domain.post.service;

import com.wanted.wantedpreonboardingbackend.domain.company.entity.Company;
import com.wanted.wantedpreonboardingbackend.domain.company.repository.CompanyRepository;
import com.wanted.wantedpreonboardingbackend.domain.post.dto.request.JobPostingSaveRequestDTO;
import com.wanted.wantedpreonboardingbackend.domain.post.dto.response.JobPostDetailResponseDTO;
import com.wanted.wantedpreonboardingbackend.domain.post.dto.response.JobPostingResponseDTO;
import com.wanted.wantedpreonboardingbackend.domain.post.entity.JobPosting;
import com.wanted.wantedpreonboardingbackend.domain.post.repository.JobPostingRepository;
import com.wanted.wantedpreonboardingbackend.domain.post.dto.request.JobPostingUpdateRequestDTO;
import com.wanted.wantedpreonboardingbackend.global.exception.ErrorCode;
import com.wanted.wantedpreonboardingbackend.global.exception.JobFlatformException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class JobPostingService {

    private final JobPostingRepository jobPostingRepository;
    private final CompanyRepository companyRepository;


    /**
     * 채용공고 등록
     */
    @Transactional
    public void save(JobPostingSaveRequestDTO request) {

        Company company = getCompanyById(request.getCompanyId());

        JobPosting jobPosting = JobPosting.builder()
                .company(company)
                .position(request.getPosition())
                .reward(request.getReward())
                .description(request.getDescription())
                .technologies(request.getTechnologies())
                .build();

        jobPostingRepository.save(jobPosting);
    }

    /**
     * 채용공고 수정
     */
    @Transactional
    public void update(Long jobPostId, JobPostingUpdateRequestDTO request) {
        JobPosting existingJobPosting = getJobPostingById(jobPostId);
        Company company = getCompanyById(request.getCompanyId());

        validateCompanyMatch(jobPostId, request.getCompanyId());

        JobPosting updatedJobPosting = JobPosting.builder()
                .jobPostId(existingJobPosting.getJobPostId())
                .company(company)
                .position(request.getPosition() != null ? request.getPosition() : existingJobPosting.getPosition())
                .reward(request.getReward() != null ? request.getReward() : existingJobPosting.getReward())
                .description(request.getDescription() != null ? request.getDescription() : existingJobPosting.getDescription())
                .technologies(request.getTechnologies() != null ? request.getTechnologies() : existingJobPosting.getTechnologies())
                .build();

        jobPostingRepository.save(updatedJobPosting);
    }

    /**
     * 채용공고 삭제
     */
    @Transactional
    public void delete(Long jobPostId) {
        jobPostingRepository.deleteById(jobPostId);
    }

    /**
     * 채용공고 조회
     */
    public List<JobPostingResponseDTO> list() {
        return jobPostingRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * 채용공고 키워드 검색
     */
    @Transactional(readOnly = true)
    public List<JobPostingResponseDTO> searchJobPostings(String search) {
        List<JobPosting> jobPostings = jobPostingRepository.searchByKeyword(search);

        return jobPostings.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * 채용공고 상세 조회
     */
    public JobPostDetailResponseDTO getPostDetail(Long jobPostId) {
        JobPosting jobPosting = getJobPostingById(jobPostId);

        List<Long> otherJobPostIds = getOtherJobPostIds(jobPosting);

        return new JobPostDetailResponseDTO(
                jobPosting.getJobPostId(),
                jobPosting.getCompany().getCompanyName(),
                jobPosting.getCompany().getCountry(),
                jobPosting.getCompany().getLocation(),
                jobPosting.getPosition(),
                jobPosting.getDescription(),
                jobPosting.getReward(),
                jobPosting.getTechnologies(),
                otherJobPostIds
        );
    }


    /**
     * companyId를 사용하여 회사 조회
     *
     * @param companyId
     * @return 해당 companyId를 가진 회사 객체
     * @throws 존재하지 않는 회사일 경우, 오류 코드와 함께 예외 발생
     */
    public Company getCompanyById(Long companyId) {
        return companyRepository.findById(companyId)
                .orElseThrow(() -> new JobFlatformException(ErrorCode.COMPANY_NOT_FOUNT));
    }

    /**
     * jobPostingId를 사용하여 채용공고 조회
     *
     * @param jobPostingId
     * @return 해당 jobPostingId를 가진 채용공고 객체
     * @throws 존재하지 않는 채용공고일 경우, 오류 코드와 함께 예외 발생
     */
    private JobPosting getJobPostingById(Long jobPostingId) {
        return jobPostingRepository.findById(jobPostingId)
                .orElseThrow(() -> new JobFlatformException(ErrorCode.JOB_POST_NOT_FOUNT));
    }

    /**
     * companyId가 작성한 채용공고가 맞는지 확인
     *
     * @param jobPostId
     * @param conpanyId
     * @throws 해당 회사가 작성하지 않은 채용 공고일 경우 오류 코드와 함께 예외 발생
     */
    private void validateCompanyMatch(Long jobPostId, Long conpanyId) {
        if (!jobPostingRepository.existsByIdAndCompanyId(jobPostId, conpanyId)) {
            throw new JobFlatformException(ErrorCode.INVALID_COMPANY_ACCESS);
        }
    }

    /**
     * JobPosting 엔티티를 JobPostingResponseDTO로 변환
     */
    private JobPostingResponseDTO convertToDTO(JobPosting jobPosting) {
        return new JobPostingResponseDTO(
                jobPosting.getJobPostId(),
                jobPosting.getCompany().getCompanyName(),
                jobPosting.getCompany().getCountry(),
                jobPosting.getCompany().getLocation(),
                jobPosting.getPosition(),
                jobPosting.getReward(),
                jobPosting.getTechnologies()
        );
    }

    /**
     * company_id를 기준으로 다른 채용공고 ID 리스트 조회
     */
    private List<Long> getOtherJobPostIds(JobPosting jobPosting) {
        return jobPostingRepository.findByCompanyId(jobPosting.getCompany().getCompanyId())
                .stream()
                .filter(post -> !post.getJobPostId().equals(jobPosting.getJobPostId()))
                .map(JobPosting::getJobPostId)
                .collect(Collectors.toList());
    }

}
