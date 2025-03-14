package com.example.bancoapi.Controller;

import com.example.bancoapi.Model.News;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/news")
public class NewsController {
    private final List<News> newsList = new ArrayList<>();

    @GetMapping
    public List<News> getAllNews() {
        return newsList;
    }

    @PostMapping
    public News createNews(@RequestBody News news) {
        newsList.add(news);
        return news;
    }

    @GetMapping("/{id}")
    public News getNewsById(@PathVariable Long id) {
        return newsList.stream()
                .filter(news -> news.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}