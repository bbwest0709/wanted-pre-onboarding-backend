package com.wanted.wantedpreonboardingbackend.domain.post;

import com.wanted.wantedpreonboardingbackend.domain.company.entity.Company;
import com.wanted.wantedpreonboardingbackend.domain.company.repository.CompanyRepository;
import com.wanted.wantedpreonboardingbackend.domain.post.dto.request.JobPostingUpdateRequestDTO;
import com.wanted.wantedpreonboardingbackend.domain.post.dto.response.JobPostDetailResponseDTO;
import com.wanted.wantedpreonboardingbackend.domain.post.dto.response.JobPostingResponseDTO;
import com.wanted.wantedpreonboardingbackend.domain.post.entity.JobPosting;
import com.wanted.wantedpreonboardingbackend.domain.post.repository.JobPostingRepository;
import com.wanted.wantedpreonboardingbackend.domain.post.service.JobPostingService;
import com.wanted.wantedpreonboardingbackend.domain.user.entity.User;
import com.wanted.wantedpreonboardingbackend.global.exception.ErrorCode;
import com.wanted.wantedpreonboardingbackend.global.exception.JobFlatformException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class JobPostingServiceTest {

    @Mock
    private JobPostingRepository jobPostingRepository;

    @Mock
    private CompanyRepository companyRepository;

    @InjectMocks
    private JobPostingService jobPostingService;

    private User user;
    private Company company;
    private JobPosting existingJobPosting;
    private JobPostingUpdateRequestDTO updateRequest;
    private JobPosting expectedUpdatedJobPosting;
    private List<JobPosting> jobPostings;
    private List<JobPostingResponseDTO> expectedJobPostingResponseDTO;

    @BeforeEach
    void setUp() {
        company = createCompany();
        existingJobPosting = createJobPosting("프론트엔드 개발자", 50000, "javascript", company, 1L, "프론트엔드 개발자를 찾습니다.");
        expectedUpdatedJobPosting = createJobPosting("백엔드 개발자", 100000, "java", company, 1L, "원티드코리아에서 프론트엔드 개발자를 채용합니다.");
        jobPostings = List.of(
                existingJobPosting,
                createJobPosting("백엔드 개발자", 100000, "java", company, 2L, "백엔드 개발자를 찾습니다."),
                createJobPosting("백엔드 개발자2", 200000, "c", company, 2L, "C언어 백엔드 개발자를 찾습니다.")
        );
        user = createUser("testUser");
        expectedJobPostingResponseDTO = jobPostings.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Test
    @DisplayName("채용공고 등록")
    void save() {
        JobPosting posting = createJobPosting("백엔드 개발자", 1000000, "java", company);

        // given
        given(jobPostingRepository.save(posting)).willReturn(posting);

        // When
        JobPosting savedPosting = jobPostingRepository.save(posting);

        // Then
        assertEntityEquals(posting, savedPosting);
    }

    @Test
    @DisplayName("채용 공고 수정")
    void update() {
        // Given
        updateRequest = createUpdateRequest();
        given(jobPostingRepository.findById(1L)).willReturn(Optional.of(existingJobPosting));
        given(jobPostingRepository.existsByIdAndCompanyId(1L, 1L)).willReturn(true);

        // When
        jobPostingService.update(1L, updateRequest);

        // Then
        verify(jobPostingRepository).existsByIdAndCompanyId(1L, 1L);

        // update 메서드에서 호출된 경우만 검증
        assertEquals(updateRequest.getPosition(), existingJobPosting.getPosition(), "Position 값이 일치하지 않습니다.");
        assertEquals(updateRequest.getReward(), existingJobPosting.getReward(), "Reward 값이 일치하지 않습니다.");
        assertEquals(updateRequest.getTechnologies(), existingJobPosting.getTechnologies(), "Technologies 값이 일치하지 않습니다.");

        // description은 업데이트 요청에서 null로 설정되므로 기존 값을 유지하는지 확인합니다.
        assertEquals(existingJobPosting.getDescription(), existingJobPosting.getDescription(), "Description 값이 일치하지 않습니다.");
    }


    @Test
    @DisplayName("채용공고 삭제")
    void delete() {
        // Given
        Long jobPostId = 1L;

        // When
        jobPostingService.delete(jobPostId);

        // Then
        verify(jobPostingRepository, times(1)).deleteById(jobPostId);
    }

    @Test
    @DisplayName("채용공고 조회")
    void list() {
        // Given
        given(jobPostingRepository.findAll()).willReturn(jobPostings);

        // When
        List<JobPostingResponseDTO> actualJobPostingResponseDTO = jobPostingService.list();

        // Then
        assertEquals(expectedJobPostingResponseDTO.size(), actualJobPostingResponseDTO.size(), "DTO 리스트의 크기가 일치하지 않습니다.");
    }

    @Test
    @DisplayName("채용공고 키워드 검색")
    void search() {
        // Given
        String searchKeyword = "백엔드";

        // 키워드와 관련된 채용 공고만 조회
        List<JobPosting> filteredJobPostings = jobPostings.stream()
                .filter(jobPosting -> jobPosting.getPosition().contains(searchKeyword))
                .collect(Collectors.toList());

        // given
        given(jobPostingRepository.searchByKeyword(searchKeyword)).willReturn(filteredJobPostings);

        // When
        List<JobPostingResponseDTO> actualJobPostingResponseDTOs = jobPostingService.searchJobPostings(searchKeyword);

        // Then
        assertEquals(2, actualJobPostingResponseDTOs.size(), "DTO 리스트의 크기가 2가 아닙니다.");
    }

    @Test
    @DisplayName("채용공고 상세 조회")
    void detail() {
        // Given
        Long jobPostId = 2L;
        JobPosting jobPosting = findJobPostingById(jobPostId);
        List<Long> otherJobPostIds = getOtherJobPostIds(jobPostId, jobPosting.getCompany());
        JobPostDetailResponseDTO expectedResponseDTO = createExpectedResponseDTO(jobPosting, otherJobPostIds);

        // given
        given(jobPostingRepository.findById(jobPostId)).willReturn(Optional.of(jobPosting));
        given(jobPostingRepository.findByCompanyId(jobPosting.getCompany().getCompanyId()))
                .willReturn(jobPostings.stream()
                        .filter(post -> post.getCompany().equals(jobPosting.getCompany()))
                        .collect(Collectors.toList()));

        // When
        JobPostDetailResponseDTO actualResponseDTO = jobPostingService.getPostDetail(jobPostId);

        // Then
        assertNotNull(actualResponseDTO.getDescription(), "채용공고 상세 Description이 조회되지 않았습니다.");
        assertEquals(expectedResponseDTO.getOtherJobPostIds(), actualResponseDTO.getOtherJobPostIds(), "OtherJobPostIds 값이 일치하지 않습니다.");
    }


    private Company createCompany() {
        return Company.builder()
                .companyId(1L)
                .companyName("원티드코리아")
                .country("한국")
                .location("부산")
                .build();
    }

    private JobPosting createJobPosting(String position, Integer reward, String technologies, Company company) {
        return createJobPosting(position, reward, technologies, company, null, null);
    }

    private JobPostingUpdateRequestDTO createUpdateRequest() {
        return JobPostingUpdateRequestDTO.builder()
                .companyId(1L)
                .position("백엔드 개발자")
                .reward(100000)
                .description(null)
                .technologies("java")
                .build();
    }

    private User createUser(String name) {
        return User.builder()
                .name(name)
                .build();
    }

    private void assertEntityEquals(Object expected, Object actual) {
        assertEquals(expected, actual, "객체가 올바르게 저장되지 않았습니다.");
    }

    private JobPosting createJobPosting(String position, Integer reward, String technologies, Company company, Long jobPostId, String description) {
        return JobPosting.builder()
                .jobPostId(jobPostId)
                .company(company)
                .position(position)
                .reward(reward)
                .description(description)
                .technologies(technologies)
                .build();
    }

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

    private JobPosting findJobPostingById(Long jobPostId) {
        return jobPostings.stream()
                .filter(post -> post.getJobPostId().equals(jobPostId))
                .findFirst()
                .orElseThrow(() -> new JobFlatformException(ErrorCode.JOB_POST_NOT_FOUNT));
    }

    private List<Long> getOtherJobPostIds(Long jobPostId, Company company) {
        return jobPostings.stream()
                .filter(post -> !post.getJobPostId().equals(jobPostId) && post.getCompany().equals(company))
                .map(JobPosting::getJobPostId)
                .collect(Collectors.toList());
    }

    private JobPostDetailResponseDTO createExpectedResponseDTO(JobPosting jobPosting, List<Long> otherJobPostIds) {
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
}