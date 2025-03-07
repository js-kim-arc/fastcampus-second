package com.fastcampus.projectboard.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/articles")
@Controller
public class ArticleController {

    //기본 홈페이지 처럼
    @GetMapping("")
    public String articles(ModelMap map){
        map.addAttribute("articles", List.of());
        return "articles/index";
    }

    //상세 페이지 들어가면 -
    @GetMapping("/{articleId}")
    public String article(@PathVariable Long articleId ,ModelMap map){
        map.addAttribute("article", List.of());
        map.addAttribute("articleComment",List.of());
        return "articles/index";
    }




}