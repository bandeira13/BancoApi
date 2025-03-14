package com.example.bancoapi.Controller;

import com.example.bancoapi.Model.News;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/news")
public class NewsController {
    private final List<News> newsList = new ArrayList<>();

    @GetMapping
    public ResponseEntity<List<News>> getAllNews() {
        return ResponseEntity.ok(newsList);
    }

    @PostMapping
    public ResponseEntity<News> createNews(@RequestBody News news) {
        if (news.getTitle() == null || news.getContent() == null) {
            return ResponseEntity.badRequest().build();
        }

        news.setId(UUID.randomUUID().toString()); // Gerar um ID único
        newsList.add(news);
        return ResponseEntity.status(HttpStatus.CREATED).body(news);
    }

    @GetMapping("/{id}")
    public ResponseEntity<News> getNewsById(@PathVariable String id) {
        News news = newsList.stream()
                .filter(n -> n.getId().equals(id))
                .findFirst()
                .orElse(null);

        if (news == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(news);
    }
}