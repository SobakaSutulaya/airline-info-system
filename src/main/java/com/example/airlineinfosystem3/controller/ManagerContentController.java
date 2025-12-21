package com.example.airlineinfosystem3.controller;

import com.example.airlineinfosystem3.model.PageContent;
import com.example.airlineinfosystem3.repository.PageContentRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/manage")
@PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
public class ManagerContentController {

    private final PageContentRepository pageContentRepository;

    public ManagerContentController(PageContentRepository pageContentRepository) {
        this.pageContentRepository = pageContentRepository;
    }

    // ========================= HOME EDIT =========================

    @GetMapping("/home/edit")
    public String editHome(Model model) {
        PageContent home = pageContentRepository.findById("HOME")
                .orElseGet(() -> new PageContent(
                        "HOME",
                        "Airline Information System",
                        "Главная страница",
                        "Добро пожаловать!"
                ));

        model.addAttribute("title", "Редактирование: Главная");
        model.addAttribute("content", home);
        return "manage-home-edit";
    }

    @PostMapping("/home/save")
    public String saveHome(@ModelAttribute("content") PageContent form) {

        PageContent existing = pageContentRepository.findById("HOME")
                .orElseGet(() -> new PageContent("HOME", "", "", ""));

        existing.setPageKey("HOME");
        existing.setTitle(nvl(form.getTitle()));
        existing.setContent(nvl(form.getContent()));
        existing.setBody(nvl(form.getBody()));

        pageContentRepository.save(existing);

        return "redirect:/?updated";
    }

    // ========================= ABOUT EDIT =========================

    @GetMapping("/about/edit")
    public String editAbout(Model model) {
        PageContent about = pageContentRepository.findById("ABOUT")
                .orElseGet(() -> new PageContent(
                        "ABOUT",
                        "About",
                        "О нас",
                        "Текст страницы «О нас»"
                ));

        model.addAttribute("title", "Редактирование: О нас");
        model.addAttribute("content", about);
        return "manage-about-edit";
    }

    @PostMapping("/about/save")
    public String saveAbout(@ModelAttribute("content") PageContent form) {

        PageContent existing = pageContentRepository.findById("ABOUT")
                .orElseGet(() -> new PageContent("ABOUT", "", "", ""));

        existing.setPageKey("ABOUT");
        existing.setTitle(nvl(form.getTitle()));
        existing.setContent(nvl(form.getContent()));
        existing.setBody(nvl(form.getBody()));

        pageContentRepository.save(existing);

        return "redirect:/about?updated";
    }

// ========================= RADAR EDIT =========================

    @GetMapping("/radar/edit")
    public String editRadar(Model model) {

        PageContent radar = pageContentRepository.findById("RADAR")
                .orElse(new PageContent(
                        "RADAR",
                        "Radar",
                        "Онлайн-радар рейсов",
                        "Текст страницы «Радар»"
                ));

        model.addAttribute("title", "Edit Radar");
        model.addAttribute("content", radar);
        return "manage-radar-edit";
    }

    @PostMapping("/radar/save")
    public String saveRadar(@ModelAttribute("content") PageContent form) {

        PageContent existing = pageContentRepository.findById("RADAR")
                .orElse(new PageContent("RADAR", "Radar", "", ""));

        existing.setPageKey("RADAR");
        existing.setTitle(form.getTitle());
        existing.setContent(form.getContent());
        existing.setBody(form.getBody());

        pageContentRepository.save(existing);

        return "redirect:/radar?updated";
    }



    private String nvl(String s) {
        return (s == null) ? "" : s.trim();
    }
}
