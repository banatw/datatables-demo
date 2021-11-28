package com.example.datatablesdemo.model;

import java.util.List;

import com.example.datatablesdemo.entity.Mahasiswa;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DatatablesModel {
    private int draw;
    private Long recordsTotal;
    private int recordsFiltered;
    private List<Mahasiswa> data;

}
