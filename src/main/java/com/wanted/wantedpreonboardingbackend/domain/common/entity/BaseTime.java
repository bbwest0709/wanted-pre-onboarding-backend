package com.wanted.wantedpreonboardingbackend.domain.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseTime {

    @CreatedDate
    @Column(name="create_at", updatable = false)
    private LocalDateTime createAt;

    @CreatedDate
    @Column(name="update_at", updatable = false)
    private LocalDateTime updateAt;

}
