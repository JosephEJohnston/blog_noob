package com.noob.blog.web;

import com.noob.blog.service.TagService;
import com.noob.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AboutShowController {

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("types", typeService.listTypeTop(3));
        model.addAttribute("tags", tagService.listTagTop(3));
        return "about";
    }
}
