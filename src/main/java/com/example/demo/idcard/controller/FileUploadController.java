package com.example.demo.idcard.controller;

import com.example.demo.idcard.model.Profile;
import com.example.demo.idcard.service.FileStorageService;
import com.example.demo.idcard.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/upload")
@RequiredArgsConstructor
public class FileUploadController {

    private final FileStorageService fileService;
    private final ProfileService profileService;

    @PostMapping("/{profileId}")
    public String uploadPhoto(
            @PathVariable Long profileId,
            @RequestParam("file") MultipartFile file) {

        try {
            // save file
            String filename = fileService.saveFile(file);

            // update profile with photo path
            Profile profile = profileService.getById(profileId);
            profile.setPhotoFileName(filename);
            profileService.create(profile);

            return "File uploaded successfully: " + filename;

        } catch (Exception e) {
            return "Upload failed: " + e.getMessage();
        }
    }
}