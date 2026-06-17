package com.example.demo.idcard.controller;

import com.example.demo.idcard.model.Profile;
import com.example.demo.idcard.service.ProfileService;
import com.example.demo.idcard.service.PdfService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pdf")
@RequiredArgsConstructor
public class PdfController {

    private final PdfService pdfService;
    private final ProfileService profileService;

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> downloadPdf(@PathVariable Long id) {

        Profile profile = profileService.getById(id);

        byte[] pdf = pdfService.generatePdf(profile).toByteArray();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=idcard.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
}