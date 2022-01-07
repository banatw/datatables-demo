package com.example.datatablesdemo.repository;

import java.util.List;

import com.example.datatablesdemo.entity.City;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepo extends JpaRepository<City,Integer> {
    Page<City> findByNameContainsIgnoreCase(String name,Pageable pageable);
}
