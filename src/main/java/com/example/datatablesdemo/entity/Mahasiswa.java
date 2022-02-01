package com.example.datatablesdemo.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.GenericGenerator;
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
    @GenericGenerator(name = "nanoid", strategy = "com.example.datatablesdemo.config.MyCustomIdGenerator")
    @GeneratedValue(generator = "nanoid")
    @Column(length = 30)
    private String idMahasiswa;

    @NonNull
    @NotBlank(message = "nama tidak boleh kosong")
    private String nama;

    @NonNull
    @NotBlank(message = "alamat tidak boleh kosong")
    private String address;

    @UpdateTimestamp
    private LocalDateTime auditDate;
}
