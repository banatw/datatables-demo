package com.example.datatablesdemo.controller;

import java.util.Optional;

import javax.validation.Valid;

import com.example.datatablesdemo.EnumAction;
import com.example.datatablesdemo.entity.Mahasiswa;
import com.example.datatablesdemo.repository.MahasiswaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


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

    @Secured({"ROLE_ADMIN","ROLE_USER"})
    @GetMapping(value="/form")
    public String form(Model model,@RequestParam("id")  Optional<Integer> id) {
        id.ifPresentOrElse(x->{
             model.addAttribute("mahasiswa", mahasiswaRepository.findById(id.get()));
        }, () -> model.addAttribute("mahasiswa", new Mahasiswa()));
        
        return "form";
    }

    @Secured({"ROLE_ADMIN","ROLE_USER"})
    @PostMapping(value="/form")
    public String simpan (@Valid Mahasiswa mahasiswa,BindingResult bindingResult,Model model) {
       if(bindingResult.hasErrors()) {
         model.addAttribute("mahasiswa", mahasiswa);
         return "form";
       }
       mahasiswaRepository.save(mahasiswa);
       return "redirect:/";
    }

    @Secured({"ROLE_ADMIN","ROLE_USER"})
    @GetMapping(value="/form-delete")
    public String deleteMahasiswa(@RequestParam("id") Optional<Integer> id) {
        id.ifPresentOrElse(data -> {
            mahasiswaRepository.deleteById(id.get());
        }, null);
        return "redirect:/";
    }

}
