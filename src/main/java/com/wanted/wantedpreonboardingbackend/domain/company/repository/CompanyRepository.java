package com.wanted.wantedpreonboardingbackend.domain.company.repository;

import com.wanted.wantedpreonboardingbackend.domain.company.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {

}
