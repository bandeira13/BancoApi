package com.example.bancoapi.Controller;

import com.example.bancoapi.Model.Features;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/features")
public class FeatureController {
    private final List<Features> features = new ArrayList<>();

    @GetMapping
    public ResponseEntity<List<Features>> getAllFeatures() {
        return ResponseEntity.ok(features);
    }

    @PostMapping
    public ResponseEntity<Features> createFeature(@RequestBody Features feature) {
        if (feature.getName() == null || feature.getDescription() == null) {
            return ResponseEntity.badRequest().build();
        }

        feature.setId(String.valueOf(Long.valueOf(UUID.randomUUID().toString()))); // Gerar um ID único
        features.add(feature);
        return ResponseEntity.status(HttpStatus.CREATED).body(feature);
    }
}