package com.ssau.lab2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping({
        "/",
        "/{path:[^\\.]*}",
        "/**/{path:[^\\.]*}" // Обработка вложенных путей (например, /projects/4)
    })
    public String forward() {
        return "forward:/index.html";
    }
}