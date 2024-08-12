package com.wanted.wantedpreonboardingbackend.domain.user.controller;

import com.wanted.wantedpreonboardingbackend.domain.user.dto.request.ApplyRequestDTO;
import com.wanted.wantedpreonboardingbackend.domain.user.service.ApplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/apply")
public class ApplyController {

    private final ApplyService applyService;

    /**
     * 채용공고 지원
     */
    @PostMapping
    public void apply(@RequestBody ApplyRequestDTO request) {
        applyService.save(request);
    }

}
