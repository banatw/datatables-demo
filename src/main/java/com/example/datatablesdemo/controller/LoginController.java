package com.example.datatablesdemo.controller;

import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.Map;
import java.util.Optional;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import com.example.datatablesdemo.entity.AppUser;
import com.example.datatablesdemo.repository.AppUserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.apiclub.captcha.Captcha;
import cn.apiclub.captcha.backgrounds.GradiatedBackgroundProducer;
import cn.apiclub.captcha.noise.CurvedLineNoiseProducer;
import cn.apiclub.captcha.text.producer.DefaultTextProducer;
import cn.apiclub.captcha.text.renderer.DefaultWordRenderer;

/**
 * LoginController
 */
@Controller
public class LoginController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AppUserRepository appUserRepository;

    @GetMapping(value = "/login")
    public String showLogin(Model model, HttpServletRequest request) {
        Captcha captcha = new Captcha.Builder(240, 70)
                .addBackground(new GradiatedBackgroundProducer())
                .addText(new DefaultTextProducer(), new DefaultWordRenderer())
                .addNoise(new CurvedLineNoiseProducer())
                .build();
        String image = null;
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ImageIO.write(captcha.getImage(), "jpg", bos);
            byte[] byteArray = Base64.getEncoder().encode(bos.toByteArray());
            image = new String(byteArray);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // captchaModel.setHiddenCaptcha(captcha.getAnswer());
        // captchaModel.setRealCaptcha(image);
        // captchaModel.setCaptcha("");
        request.getSession().setAttribute("answer", captcha.getAnswer());
        // System.out.println(captchaModel.getHiddenCaptcha());
        model.addAttribute("captchaImage", image);
        return "login";
    }

    @PostMapping(value = "/doLogin")
    public String doLogin(@RequestParam Map<String, String> loginParam, HttpServletRequest request) {
        //TODO: process POST request
        String username = loginParam.get("username");
        String password = loginParam.get("password");
        String captcha = loginParam.get("captcha");
        String answer = request.getSession().getAttribute("answer").toString();
        if (captcha.equals(answer)) {
            Optional<AppUser> appUser = appUserRepository.findByUsername(username);
            if (!appUser.isEmpty()) {
                if (new BCryptPasswordEncoder().matches(password, appUser.get().getPassword())) {
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                            username, password);
                    Authentication authentication = authenticationManager
                            .authenticate(usernamePasswordAuthenticationToken);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    return "redirect:/";
                }
                return "redirect:/login";

            }
        }

        // System.out.println(appUser.getPassword());
        return "redirect:/login";
    }

}