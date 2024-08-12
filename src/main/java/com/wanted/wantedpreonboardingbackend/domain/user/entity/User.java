package com.wanted.wantedpreonboardingbackend.domain.user.entity;

import com.wanted.wantedpreonboardingbackend.domain.common.entity.BaseTime;
import jakarta.persistence.*;
import lombok.*;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "user")
public class User extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Builder
    public User(String name) {
        this.name = name;
    }

}
