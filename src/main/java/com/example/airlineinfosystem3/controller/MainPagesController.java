package com.example.airlineinfosystem3.controller;

import com.example.airlineinfosystem3.model.PageContent;
import com.example.airlineinfosystem3.repository.PageContentRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainPagesController {

    private final PageContentRepository pageContentRepository;

    public MainPagesController(PageContentRepository pageContentRepository) {
        this.pageContentRepository = pageContentRepository;
    }

    @GetMapping("/")
    public String home(Model model) {
        PageContent home = pageContentRepository.findById("HOME")
                .orElse(new PageContent("HOME", "Airline Information System", "Добро пожаловать!", "Добро пожаловать!"));

        model.addAttribute("title", home.getTitle());
        model.addAttribute("homeText", home.getBody());
        return "index";
    }

    @GetMapping("/about")
    public String about(Model model) {
        PageContent about = pageContentRepository.findById("ABOUT")
                .orElse(new PageContent("ABOUT", "About", "О системе", "Текст страницы «О нас»"));

        model.addAttribute("title", about.getTitle());
        model.addAttribute("aboutText", about.getBody());
        return "about";
    }

    @GetMapping("/radar")
    public String radar(Model model) {
        PageContent radar = pageContentRepository.findById("RADAR")
                .orElse(new PageContent("RADAR", "Radar", "Радар", "Текст страницы «Радар»"));

        model.addAttribute("title", radar.getTitle());
        model.addAttribute("radarShort", radar.getContent());
        model.addAttribute("radarText", radar.getBody());

        return "radar";
    }

}
