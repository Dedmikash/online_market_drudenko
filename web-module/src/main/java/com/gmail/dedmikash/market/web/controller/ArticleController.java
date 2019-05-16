package com.gmail.dedmikash.market.web.controller;

import com.gmail.dedmikash.market.service.ArticleService;
import com.gmail.dedmikash.market.service.model.ArticleDTO;
import com.gmail.dedmikash.market.service.model.PageDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/articles")
public class ArticleController {
    private static final Logger logger = LoggerFactory.getLogger(ArticleController.class);
    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping
    public String getArticles(@RequestParam(name = "page", defaultValue = "1") Integer page,
                              @RequestParam(name = "sort", defaultValue = "date") String sort,
                              Model model) {
        PageDTO<ArticleDTO> articles = articleService.getArticles(page, sort);
        model.addAttribute("articles", articles.getList());
        model.addAttribute("pages", articles.getCountOfPages());
        model.addAttribute("sort", sort);
        logger.info("Getting articles {}, page {}", articles.getList(), page);
        return "articles";
    }

    @GetMapping("/{id}")
    public String getArticleById(@PathVariable(name = "id") Long id,
                                 Model model) {
        ArticleDTO article = articleService.getArticleById(id);
        //List<CommentDTO> comments = commentService.getCommentsByArticleId(id);
        model.addAttribute("article", article);
        //model.addAttribute("comments", comments);
        logger.info("Getting article with id {}", id);
        return "article";
    }
}
