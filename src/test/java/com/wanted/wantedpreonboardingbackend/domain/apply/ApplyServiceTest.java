package com.wanted.wantedpreonboardingbackend.domain.apply;

import com.wanted.wantedpreonboardingbackend.domain.company.entity.Company;
import com.wanted.wantedpreonboardingbackend.domain.post.entity.JobPosting;
import com.wanted.wantedpreonboardingbackend.domain.post.repository.JobPostingRepository;
import com.wanted.wantedpreonboardingbackend.domain.user.dto.request.ApplyRequestDTO;
import com.wanted.wantedpreonboardingbackend.domain.user.entity.Apply;
import com.wanted.wantedpreonboardingbackend.domain.user.entity.User;
import com.wanted.wantedpreonboardingbackend.domain.user.repository.ApplyRepository;
import com.wanted.wantedpreonboardingbackend.domain.user.repository.UserRepository;
import com.wanted.wantedpreonboardingbackend.domain.user.service.ApplyService;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ApplyServiceTest {

    @Mock
    private ApplyRepository applyRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private JobPostingRepository jobPostingRepository;

    @InjectMocks
    private ApplyService applyService;

    private User user;
    private JobPosting jobPosting;
    private ApplyRequestDTO request;

    @BeforeEach
    void setUp() {
        Company company = createCompany();
        jobPosting = createJobPosting("백엔드 개발자", 100000, "java", company, 2L, "백엔드 개발자를 찾습니다.");
        user = createUser("testUser");

        request = ApplyRequestDTO.builder()
                .id(user.getId())
                .jobPostId(jobPosting.getJobPostId())
                .build();
    }

    @Test
    @DisplayName("채용공고 지원 : 중복 지원 아닐 경우")
    void applyNotApplied() {
        // Given
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(jobPostingRepository.findById(jobPosting.getJobPostId())).thenReturn(Optional.of(jobPosting));
        when(applyRepository.existsByUserIdAndJobPostId(user.getId(), jobPosting.getJobPostId())).thenReturn(false);

        // When
        applyService.save(request);

        // Then
        ArgumentCaptor<Apply> applyArgumentCaptor = ArgumentCaptor.forClass(Apply.class);
        verify(applyRepository).save(applyArgumentCaptor.capture());
        Apply savedApply = applyArgumentCaptor.getValue();

        assertNotNull(savedApply, "Apply 객체가 null이 아니어야 합니다.");
        assertEquals(user, savedApply.getUser(), "저장된 Apply 객체의 사용자가 요청된 사용자와 일치해야 합니다.");
        assertEquals(jobPosting, savedApply.getJobPosting(), "저장된 Apply 객체의 채용공고가 요청된 채용공고와 일치해야 합니다.");
    }

    @Test
    @DisplayName("채용공고 지원 : 중복 지원일 경우")
    void applyAlreadyApplied() {
        // Given
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(jobPostingRepository.findById(jobPosting.getJobPostId())).thenReturn(Optional.of(jobPosting));
        when(applyRepository.existsByUserIdAndJobPostId(user.getId(), jobPosting.getJobPostId())).thenReturn(true);

        // When & Then
        JobFlatformException thrown = assertThrows(JobFlatformException.class, () -> applyService.save(request));
        assertEquals(ErrorCode.ALREADY_APPLIED.getMessage(), thrown.getMessage(), "Exception message should match");
    }

    private Company createCompany() {
        return Company.builder()
                .companyId(1L)
                .companyName("원티드코리아")
                .country("한국")
                .location("부산")
                .build();
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

    private User createUser(String name) {
        return User.builder()
                .name(name)
                .build();
    }
}
