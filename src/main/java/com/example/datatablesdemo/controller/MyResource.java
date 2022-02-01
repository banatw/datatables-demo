package com.example.datatablesdemo.controller;

import java.util.List;
import java.util.Optional;

import com.example.datatablesdemo.entity.City;
import com.example.datatablesdemo.entity.Mahasiswa;
import com.example.datatablesdemo.model.DatatablesModel;
import com.example.datatablesdemo.repository.CityRepo;
import com.example.datatablesdemo.repository.MahasiswaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyResource {

    @Autowired
    private MahasiswaRepository repo;

    @Autowired
    private CityRepo cityRepo;

    @Secured({ "ROLE_ADMIN", "ROLE_USER" })
    @GetMapping(value = "mahasiswa/{page}/{size}")
    public List<Mahasiswa> getMahasiswa(@PathVariable("page") int page, @PathVariable("size") int size) {
        return repo.findAll(PageRequest.of(page, size)).toList();
    }

    // @CrossOrigin
    @Secured({ "ROLE_ADMIN", "ROLE_USER" })
    @GetMapping(value = "/mahasiswas")
    public DatatablesModel getAll(@RequestParam(required = false, name = "draw", defaultValue = "0") Integer draw,
            @RequestParam("length") Integer length, @RequestParam("start") Integer start,
            @RequestParam(name = "search[value]", required = false, defaultValue = "") String search) {
        Integer page = start / length;
        Pageable pageable = PageRequest.of(page, length).withSort(Direction.DESC, "auditDate");
        DatatablesModel datatablesModel = new DatatablesModel();
        List<Mahasiswa> mahasiswas = repo.findByNamaContainsIgnoreCase(search, pageable);
        datatablesModel.setDraw(draw);
        datatablesModel.setRecordsFiltered(repo.countByNamaContainsIgnoreCase(search));
        datatablesModel.setRecordsTotal(repo.count());
        datatablesModel.setData(mahasiswas);
        return datatablesModel;
    }

    @Secured({ "ROLE_ADMIN", "ROLE_USER" })
    @GetMapping(value = "/cities")
    public Page<City> cities(@RequestParam(required = false) Optional<String> q,
            @RequestParam(required = false) Optional<Integer> page) {
        Pageable pageable = PageRequest.of(page.orElse(0), 10, Sort.by("name").ascending());
        Page<City> page2 = cityRepo.findByNameContainsIgnoreCase(q.orElse(""), pageable);
        // System.out.println(page2.getTotalPages());
        // select2Model.setResults(page2.getContent());
        // More more = new More();
        // more.setMore(page2.getTotalPages()>page.orElse(0)?"true":"false");
        // select2Model.setPagination(more);
        return page2;
    }
}
