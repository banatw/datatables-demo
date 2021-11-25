package com.example.datatablesdemo.controller;

import java.util.List;

import com.example.datatablesdemo.entity.Mahasiswa;
import com.example.datatablesdemo.model.DatatablesModel;
import com.example.datatablesdemo.repository.MahasiswaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyResource {

    @Autowired
    private MahasiswaRepository repo;

    @GetMapping(value = "mahasiswa/{page}/{size}")
    public List<Mahasiswa> getMahasiswa(@PathVariable("page") int page, @PathVariable("size") int size) {
        return repo.findAll(PageRequest.of(page, size)).toList();
    }

    // @CrossOrigin
    @GetMapping(value = "/mahasiswas")
    public DatatablesModel getAll(@RequestParam(required = false, name = "draw", defaultValue = "0") Integer draw,
            @RequestParam("length") Integer length, @RequestParam("start") Integer start) {
        Integer page = start / length;
        Pageable pageable = PageRequest.of(page, length);
        DatatablesModel datatablesModel = new DatatablesModel();
        datatablesModel.setDraw(draw);
        datatablesModel.setRecordsFiltered(repo.countByNamaContainsIgnoreCase(""));
        datatablesModel.setRecordsTotal(repo.countByNamaContainsIgnoreCase(""));
        datatablesModel.setData(repo.findByNamaContainsIgnoreCase("", pageable));
        return datatablesModel;
    }

}
