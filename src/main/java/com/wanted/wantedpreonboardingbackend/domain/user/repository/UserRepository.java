package com.wanted.wantedpreonboardingbackend.domain.user.repository;

import com.wanted.wantedpreonboardingbackend.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
