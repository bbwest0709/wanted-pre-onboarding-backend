package com.wanted.wantedpreonboardingbackend.domain.company.entity;

import com.wanted.wantedpreonboardingbackend.domain.common.entity.BaseTime;
import jakarta.persistence.*;
import lombok.*;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "company")
public class Company extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long companyId;

    @Column(nullable = false)
    private String companyName;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private String location;

    @Builder
    public Company(Long companyId, String companyName, String country, String location){
        this.companyId = companyId;
        this.companyName = companyName;
        this.country = country;
        this.location = location;
    }

}
