package com.gmail.dedmikash.market.web.controller;

import com.gmail.dedmikash.market.service.ArticleService;
import com.gmail.dedmikash.market.service.model.ArticleDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/articles")
public class ArticleAPIController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final ArticleService articleService;

    public ArticleAPIController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping
    public ResponseEntity showArticles() {
        List<ArticleDTO> articleDTOList = articleService.getAllArticles();
        logger.info("All articles were shown with REST API");
        return new ResponseEntity(articleDTOList, HttpStatus.FOUND); //TODO
    }

    @GetMapping("/{id}")
    public ResponseEntity showArticle(@PathVariable("id") Long id) {
        ArticleDTO articleDTO = articleService.getArticleById(id);
        logger.info("Article with id: {} - was shown with REST API", id);
        return new ResponseEntity(articleDTO, HttpStatus.CREATED); //TODO
    }

    @PostMapping
    public ResponseEntity saveArticle(@RequestBody ArticleDTO articleDTO) {
        articleService.add(articleDTO);
        logger.info("Added article: {} - with REST API", articleDTO.getName());
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteArticle(@PathVariable("id") Long id) {
        articleService.deleteArticleById(id);
        logger.info("Article was deleted with REST API");
        return new ResponseEntity(HttpStatus.OK); //TODO
    }
}
