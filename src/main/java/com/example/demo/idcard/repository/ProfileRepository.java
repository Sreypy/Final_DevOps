package com.example.demo.idcard.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.idcard.model.Profile;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

    Optional<Profile> findByEmail(String email);

    boolean existsByEmail(String email);
}