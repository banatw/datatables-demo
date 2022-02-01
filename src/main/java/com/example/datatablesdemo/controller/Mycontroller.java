package com.example.datatablesdemo.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.example.datatablesdemo.entity.Mahasiswa;
import com.example.datatablesdemo.repository.MahasiswaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class Mycontroller {
    @Autowired
    private MahasiswaRepository mahasiswaRepository;

    @GetMapping(value = "/")
    @Secured({ "ROLE_ADMIN", "ROLE_USER" })
    public String getMethodName() {
        return "index";
    }

    @Secured({ "ROLE_ADMIN", "ROLE_USER" })
    @GetMapping(value = "/profile")
    public String getProfile(Model model, @RequestParam(name = "id", required = true) String id) {
        Mahasiswa mahasiswa = mahasiswaRepository.findById(id).get();
        model.addAttribute("mahasiswa", mahasiswa);
        return "profile";
    }

    @Secured({ "ROLE_ADMIN", "ROLE_USER" })
    @GetMapping(value = "/form")
    public String form(Model model, @RequestParam("id") Optional<String> id) {
        id.ifPresentOrElse(x -> {
            model.addAttribute("mahasiswa", mahasiswaRepository.findById(id.get()));
        }, () -> model.addAttribute("mahasiswa", new Mahasiswa()));

        return "form";
    }

    @Secured({ "ROLE_ADMIN", "ROLE_USER" })
    @PostMapping(value = "/form")
    public String simpan(@Valid Mahasiswa mahasiswa, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("mahasiswa", mahasiswa);
            return "form";
        }
        mahasiswaRepository.save(mahasiswa);
        return "redirect:/";
    }

    @Secured({ "ROLE_ADMIN", "ROLE_USER" })
    @GetMapping(value = "/form-delete")
    public String deleteMahasiswa(@RequestParam("id") Optional<String> id) {
        id.ifPresentOrElse(data -> {
            mahasiswaRepository.deleteById(id.get());
        }, null);
        return "redirect:/";
    }

    @GetMapping("/select2")
    public String getSelect2() {
        return "select2";
    }

    @Secured({ "ROLE_ADMIN", "ROLE_USER" })
    @PostMapping(value = "/simpan_list")
    public String simpanList(@RequestParam(name = "data") List<String> data) {
        data.forEach(x -> System.out.println(x));
        // System.out.println("dsdsdsd");
        return "redirect:/select2";
    }

    @Secured({ "ROLE_ADMIN", "ROLE_USER" })
    @GetMapping("/simpan_list1")
    public String simpanList1() {
        return "formlist";
    }

}
