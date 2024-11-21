package com.uzay.urun.oauth2;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class CustomLoginPageController {



    @GetMapping("/customLoginPage")
    public ModelAndView customloginUrl() {
        ModelAndView modelAndView = new ModelAndView("customloginurl"); // customloginUrl.html dosyasını döndürüyoruz
        return modelAndView;
    }

    @GetMapping("/home")
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView("home"); // customloginUrl.html dosyasını döndürüyoruz
        return modelAndView;
    }

}
