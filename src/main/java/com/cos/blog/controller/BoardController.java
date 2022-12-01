package com.cos.blog.controller;

import com.cos.blog.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BoardController {

    @Autowired
    private BoardService boardService;

    @GetMapping({"","/"})
    public String index(Model model) {
        model.addAttribute("boards", boardService.글목록());
        return "index"; //viewResolver 작동!!
    }

    @GetMapping({"/board/saveFrom"})
    public String saveFrom() {
        return "board/saveFrom";
    }
}
