package com.tongwii.ico.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * ${DESCRIPTION}
 *
 * @author Zeral
 * @date 2017-08-08
 */
@Controller
@RequestMapping("/admin")
public class AdminController extends UserController {

    @GetMapping("/test")
    public String example1(Model model)
    {
        model.addAttribute("currentUser", "test");
        return "sign";
    }
}
