package com.example.datatablesdemo.repository;

import java.util.List;

import com.example.datatablesdemo.entity.Mahasiswa;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MahasiswaRepository extends JpaRepository<Mahasiswa, String> {
    List<Mahasiswa> findByNamaContainsIgnoreCase(String nama, Pageable pageable);

    Integer countByNamaContainsIgnoreCase(String nama);

}
