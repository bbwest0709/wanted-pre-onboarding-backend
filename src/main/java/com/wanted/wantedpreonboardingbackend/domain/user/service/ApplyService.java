package com.wanted.wantedpreonboardingbackend.domain.user.service;

import com.wanted.wantedpreonboardingbackend.domain.post.entity.JobPosting;
import com.wanted.wantedpreonboardingbackend.domain.post.repository.JobPostingRepository;
import com.wanted.wantedpreonboardingbackend.domain.user.dto.request.ApplyRequestDTO;
import com.wanted.wantedpreonboardingbackend.domain.user.entity.Apply;
import com.wanted.wantedpreonboardingbackend.domain.user.entity.User;
import com.wanted.wantedpreonboardingbackend.domain.user.repository.ApplyRepository;
import com.wanted.wantedpreonboardingbackend.domain.user.repository.UserRepository;
import com.wanted.wantedpreonboardingbackend.global.exception.ErrorCode;
import com.wanted.wantedpreonboardingbackend.global.exception.JobFlatformException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ApplyService {

    private final ApplyRepository applyRepository;
    private final UserRepository userRepository;
    private final JobPostingRepository jobPostingRepository;

    /**
     * 채용공고 지원
     */
    @Transactional
    public void save(ApplyRequestDTO request) {

        User user = getUserById(request.getId());
        JobPosting jobPosting = getJobPostingById(request.getJobPostId());

        hasAlreadyApplied(request.getId(), request.getJobPostId());

        Apply apply = Apply.builder()
                .user(user)
                .jobPosting(jobPosting)
                .build();

        applyRepository.save(apply);
    }

    /**
     * 회원 ID로 회원 조회
     *
     * @param id
     * @return 해당 id를 가진 회원 조회
     * @throws 존재하지 않는 회원일 경우, 오류 코드와 함께 예외 발생
     */
    private User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new JobFlatformException(ErrorCode.USER_NOT_FOUND));
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
     * 이미 지원한 공고인지 확인
     *
     * @param id
     * @param jobPostId
     */
    public void hasAlreadyApplied(Long id, Long jobPostId) {
        if (applyRepository.existsByUserIdAndJobPostId(id, jobPostId)) {
            throw new JobFlatformException(ErrorCode.ALREADY_APPLIED);
        }
    }


}
