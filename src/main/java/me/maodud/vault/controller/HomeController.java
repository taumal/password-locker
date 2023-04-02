package me.maodud.vault.controller;

import me.maodud.vault.model.Folder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
public class HomeController {
    @GetMapping("")
    public String getFolders(Model model) {
        return "welcome";
    }
}
