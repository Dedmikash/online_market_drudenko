package com.gmail.dedmikash.market.web.controller;

import com.gmail.dedmikash.market.service.ArticleService;
import com.gmail.dedmikash.market.service.CommentService;
import com.gmail.dedmikash.market.service.model.AppUserPrincipal;
import com.gmail.dedmikash.market.service.model.ArticleDTO;
import com.gmail.dedmikash.market.service.model.CommentDTO;
import com.gmail.dedmikash.market.service.model.PageDTO;
import com.gmail.dedmikash.market.service.model.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
@RequestMapping("/articles")
public class ArticleController {
    private static final Logger logger = LoggerFactory.getLogger(ArticleController.class);
    private final ArticleService articleService;
    private final CommentService commentService;

    public ArticleController(ArticleService articleService,
                             CommentService commentService) {
        this.articleService = articleService;
        this.commentService = commentService;
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

    @GetMapping("/{article_id}/comments")
    public String getArticleById(@PathVariable(name = "article_id") Long article_id,
                                 CommentDTO commentDTO,
                                 Model model) {
        model.addAttribute("article", articleService.getArticleById(article_id));
        model.addAttribute("comments", commentService.getCommentsByArticleId(article_id));
        logger.info("Getting article with id {}", article_id);
        return "comments";
    }

    @PostMapping("/{article_id}/comments")
    public String addCommentToArticleWithId(@PathVariable(name = "article_id") Long article_id,
                                            @Valid CommentDTO commentDTO,
                                            BindingResult result,
                                            Model model) {
        model.addAttribute("article", articleService.getArticleById(article_id));
        if (result.hasErrors()) {
            logger.info("Attempt to add not valid comment: {}", commentDTO.getText());
            model.addAttribute("comments", commentService.getCommentsByArticleId(article_id));
            return "comments";
        }
        UserDTO userDTO = new UserDTO();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        userDTO.setId(((AppUserPrincipal) userDetails).getId());
        commentDTO.setUserDTO(userDTO);
        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setId(article_id);
        commentDTO.setArticleDTO(articleDTO);
        commentService.saveComment(commentDTO);
        logger.info("Added article: {}", commentDTO.getText());
        model.addAttribute("comments", commentService.getCommentsByArticleId(article_id));
        return "comments";
    }
}
