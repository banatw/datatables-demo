package com.example.datatablesdemo.controller;

import java.util.Optional;

import com.example.datatablesdemo.entity.Mahasiswa;
import com.example.datatablesdemo.repository.MahasiswaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class Mycontroller {
    @Autowired
    private MahasiswaRepository mahasiswaRepository;

    @GetMapping(value = "")
    @Secured({"ROLE_ADMIN","ROLE_USER"})
    public String getMethodName() {
        return "index";
    }

    @Secured({"ROLE_ADMIN","ROLE_USER"})
    @GetMapping(value = "/profile")
    public String getProfile(Model model, @RequestParam(name = "id", required = true) Integer id) {
        Mahasiswa mahasiswa = mahasiswaRepository.findById(id).get();
        model.addAttribute("mahasiswa", mahasiswa);
        return "profile";
    }

}
