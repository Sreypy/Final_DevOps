package com.example.demo.idcard.controller;

import com.example.demo.idcard.model.Profile;
import com.example.demo.idcard.service.FileStorageService;
import com.example.demo.idcard.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
public class ViewController {

    private final ProfileService profileService;
    private final FileStorageService fileService;

    // Dashboard: List all profiles
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("profiles", profileService.getAll());
        return "index";
    }

    // Show create form
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("profile", new Profile());
        model.addAttribute("title", "Create New Profile");
        return "create";
    }

    // Show edit form
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Profile profile = profileService.getById(id);
        model.addAttribute("profile", profile);
        model.addAttribute("title", "Edit Profile");
        return "create"; // Reusing create.html as the form
    }

    // Handle form submission (Create or Update)
    @PostMapping("/save")
    public String saveProfile(@ModelAttribute Profile profile, 
                              @RequestParam("photo") MultipartFile photo) throws Exception {
        
        // Handle photo upload if present
        if (!photo.isEmpty()) {
            String filename = fileService.saveFile(photo);
            profile.setPhotoFileName(filename);
            profile.setPhotoContentType(photo.getContentType());
        } else if (profile.getId() != null) {
            // Keep existing photo if not changed during edit
            Profile existing = profileService.getById(profile.getId());
            profile.setPhotoFileName(existing.getPhotoFileName());
            profile.setPhotoContentType(existing.getPhotoContentType());
        }

        if (profile.getUuid() == null) {
            profile.setUuid(java.util.UUID.randomUUID().toString());
        }
        
        profileService.create(profile);
        return "redirect:/";
    }

    // Delete profile
    @GetMapping("/delete/{id}")
    public String deleteProfile(@PathVariable Long id) {
        profileService.delete(id);
        return "redirect:/";
    }

    // Show preview page
    @GetMapping("/preview/{id}")
    public String preview(@PathVariable Long id, Model model) {
        Profile profile = profileService.getById(id);
        model.addAttribute("profile", profile);
        return "preview";
    }
}