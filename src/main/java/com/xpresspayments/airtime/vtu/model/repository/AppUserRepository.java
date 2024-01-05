package com.xpresspayments.airtime.vtu.model.repository;

import com.xpresspayments.airtime.vtu.model.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByPhoneNo(String phoneNo);
    Optional<AppUser> findByEmail(String email);
}
