package com.example.datatablesdemo.controller;

import com.example.datatablesdemo.repository.MahasiswaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Mycontroller {

    @GetMapping(value = "")
    public String getMethodName() {
        return "index";
    }

}
