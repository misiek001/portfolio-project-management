package com.misiek.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class ProjectController {

    @GetMapping("/hello")
    public String test() {
        return "welcome";
    }

}
