package com.example.bancoapi.Controller;

import com.example.bancoapi.Model.Features;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/features")
public class FeatureController {
    private List<Features> features = new ArrayList<>();

    @GetMapping
    public List<Features> getAllFeatures() {
        return features;
    }

    @PostMapping
    public Features createFeature(@RequestBody Features feature) {
        features.add(feature);
        return feature;
    }
}