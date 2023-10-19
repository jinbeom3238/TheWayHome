package com.btc.thewayhome.admin.member;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Log4j2
@Controller
@RequestMapping("/admin")
public class AdminHomeController {

    @GetMapping(value = {"", "/"})
    public String admin() {
        log.info("HomeController()");

        return "admin/home";

    }

}
