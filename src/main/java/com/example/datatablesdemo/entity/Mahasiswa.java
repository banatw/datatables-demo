package com.example.datatablesdemo.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class Mahasiswa {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idMahasiswa;

    @NonNull
    @NotBlank(message = "nama tidak boleh kosong")
    private String nama;

    @NonNull
    @NotBlank(message = "alamat tidak boleh kosong")
    private String address;

    @UpdateTimestamp
    private LocalDateTime auditDate;
}
