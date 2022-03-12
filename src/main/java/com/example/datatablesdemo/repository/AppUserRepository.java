package com.example.datatablesdemo.repository;

import java.util.Optional;

import com.example.datatablesdemo.entity.AppUser;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUser, Integer> {
    public Optional<AppUser> findByUsername(String username);
}
