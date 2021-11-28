package com.example.datatablesdemo.controller;

import java.util.List;

import com.example.datatablesdemo.entity.Mahasiswa;
import com.example.datatablesdemo.model.DatatablesModel;
import com.example.datatablesdemo.repository.MahasiswaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyResource {

    @Autowired
    private MahasiswaRepository repo;

    @Secured({"ROLE_ADMIN","ROLE_USER"})
    @GetMapping(value = "mahasiswa/{page}/{size}")
    public List<Mahasiswa> getMahasiswa(@PathVariable("page") int page, @PathVariable("size") int size) {
        return repo.findAll(PageRequest.of(page, size)).toList();
    }

    // @CrossOrigin
    @Secured({"ROLE_ADMIN","ROLE_USER"})
    @GetMapping(value = "/mahasiswas")
    public DatatablesModel getAll(@RequestParam(required = false, name = "draw", defaultValue = "0") Integer draw,
            @RequestParam("length") Integer length, @RequestParam("start") Integer start,
            @RequestParam(name = "search[value]", required = false, defaultValue = "") String search) {
        Integer page = start / length;
        Pageable pageable = PageRequest.of(page, length);
        DatatablesModel datatablesModel = new DatatablesModel();
        datatablesModel.setDraw(draw);
        datatablesModel.setRecordsFiltered(repo.countByNamaContainsIgnoreCase(search));
        datatablesModel.setRecordsTotal(repo.count());
        datatablesModel.setData(repo.findByNamaContainsIgnoreCase(search, pageable));
        return datatablesModel;
    }

}
