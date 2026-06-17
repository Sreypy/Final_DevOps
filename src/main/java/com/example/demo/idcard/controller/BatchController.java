package com.example.demo.idcard.controller;

import com.example.demo.idcard.model.Profile;
import com.example.demo.idcard.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/batch")
@RequiredArgsConstructor
public class BatchController {

    private final ProfileService service;

    @PostMapping
    public String generateBatch(@RequestBody List<Profile> profiles) {

        service.generateBatch(profiles);

        return "Batch generation completed!";
    }
}