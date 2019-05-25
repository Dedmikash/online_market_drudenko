package com.gmail.dedmikash.market.service.impl;

import com.gmail.dedmikash.market.repository.ArticleRepository;
import com.gmail.dedmikash.market.repository.model.Article;
import com.gmail.dedmikash.market.service.ArticleService;
import com.gmail.dedmikash.market.service.converter.ArticleConverter;
import com.gmail.dedmikash.market.service.model.ArticleDTO;
import com.gmail.dedmikash.market.service.model.CommentDTO;
import com.gmail.dedmikash.market.service.model.PageDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Collections;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ArticleServiceUnitTest {
    @Mock
    private ArticleRepository articleRepository;
    @Mock
    private ArticleConverter articleConverter;
    private ArticleService articleService;

    @Before
    public void init() {
        articleService = new ArticleServiceImpl(articleConverter, articleRepository);
    }

    @Test
    public void getArticleByIdShouldReturnArticleDTO() {
        Article article = new Article();
        article.setViews(0L);
        ArticleDTO articleDTO = new ArticleDTO();
        when(articleRepository.findById(1L)).thenReturn(article);
        when(articleConverter.toDTO(article)).thenReturn(articleDTO);
        ArticleDTO returnedArticle = articleService.getArticleById(1L);
        Assert.assertEquals(articleDTO, returnedArticle);
    }

    @Test
    public void getArticleByIdShouldIncreaseArticleViews() {
        Article article = new Article();
        article.setViews(1L);
        when(articleRepository.findById(1L)).thenReturn(article);
        articleService.getArticleById(1L);
        Assert.assertEquals(2L, article.getViews().longValue());
    }


    @Test
    public void getAllArticlesShouldReturnArticleDTOList() {
        Article article = new Article();
        when(articleRepository.findAll()).thenReturn(Collections.singletonList(article));
        ArticleDTO articleDTO = new ArticleDTO();
        when(articleConverter.toDTO(article)).thenReturn(articleDTO);
        Assert.assertEquals(new ArrayList<>(Collections.singletonList(articleDTO)), articleService.getAllArticles());
    }

    @Test
    public void getArticlesShouldReturnPageWithArticleDTOListAndPageCount() {
        Article article = new Article();
        article.setText("123");
        when(articleRepository.getArticles(1, "date", "desc")).thenReturn(Collections.singletonList(article));
        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setText("123");
        when(articleConverter.toDTO(article)).thenReturn(articleDTO);
        when(articleRepository.getCountOfPages()).thenReturn(5);
        PageDTO<ArticleDTO> page = new PageDTO<>();
        page.setCountOfPages(5);
        page.setList(Collections.singletonList(articleDTO));
        Assert.assertEquals(page.getList(), articleService.getArticles(1, "date", "desc").getList());
        Assert.assertEquals(page.getCountOfPages(), articleService.getArticles(1, "date", "desc").getCountOfPages());
    }

    @Test
    public void getArticlesShouldReturnPageWithArticleDTOListAndPageCountAndCutLongText() {
        Article article = new Article();
        article.setText("123sdfsdfsdfsdf asdasd asdasd asdasdasd asdasdasd asdasdasd asdasdasd asdasdasd asdasdasdasd asdasd" +
                "asdasdasdasd asdasdasd asdasdasd asdasdasd asdasdasd asdasdasd asdasdasd asdasdasd asdasdasd asdasdasd" +
                "asdasd asdasd asdadasdasdasdasdasdasd asdasdasd asdasdasdasd asdasdasd asdasdasd asdasdasd asdasdads asdasdasd" +
                "asdasdasd asdsada asdasda asdasdasdsad asdasdasd asdasda asdasd asdasd asdasdasd");
        when(articleRepository.getArticles(1, "date", "desc")).thenReturn(Collections.singletonList(article));
        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setText(article.getText());
        when(articleConverter.toDTO(article)).thenReturn(articleDTO);
        when(articleRepository.getCountOfPages()).thenReturn(5);
        PageDTO<ArticleDTO> page = new PageDTO<>();
        page.setCountOfPages(5);
        page.setList(Collections.singletonList(articleDTO));
        Assert.assertEquals(page.getList(), articleService.getArticles(1, "date", "desc").getList());
        Assert.assertEquals(page.getCountOfPages(), articleService.getArticles(1, "date", "desc").getCountOfPages());
        String expectedText = "123sdfsdfsdfsdf asdasd asdasd asdasdasd asdasdasd asdasdasd asdasdasd asdasdasd asdasdasdasd " +
                "asdasdasdasdasdasd asdasdasd asdasdasd asdasdasd asdasdasd asdasdasd asdasdasd asdasdasd asdasdasd...";
        Assert.assertEquals(expectedText, articleService.getArticles(1, "date", "desc").getList().get(0).getText());
    }

    @Test
    public void addCommentToArticleShouldReturnArticleDTOWithNewComment() {
        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setViews(5L);
        articleDTO.setComments(new ArrayList<>());
        CommentDTO commentDTO = new CommentDTO();
        Article article = new Article();
        when(articleConverter.fromDTO(articleDTO)).thenReturn(article);
        when(articleRepository.findById(articleDTO.getId())).thenReturn(article);
        when(articleConverter.toDTO(article)).thenReturn(articleDTO);
        Assert.assertTrue(articleService.addCommentToArticle(articleDTO, commentDTO).getComments().contains(commentDTO));
    }

    @Test
    public void changeArticleInfoShouldReturnArticleDTOWithNewInfo() {
        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setName("test name");
        articleDTO.setText("test text");
        Article article = new Article();
        when(articleRepository.findById(1L)).thenReturn(article);
        article.setName(articleDTO.getName());
        article.setText(articleDTO.getText());
        when(articleRepository.findById(1L)).thenReturn(article);
        when(articleConverter.toDTO(article)).thenReturn(articleDTO);
        Assert.assertEquals(articleDTO.getName(), articleService.changeArticleInfo(1L, articleDTO).getName());
        Assert.assertEquals(articleDTO.getText(), articleService.changeArticleInfo(1L, articleDTO).getText());
    }
}