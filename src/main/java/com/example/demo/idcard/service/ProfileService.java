package com.example.demo.idcard.service;

import com.example.demo.idcard.model.Profile;
import com.example.demo.idcard.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository repository;

    public Profile create(Profile profile) {
        return repository.save(profile);
    }

    public List<Profile> getAll() {
        return repository.findAll();
    }

    public Profile getById(Long id) {
        return repository.findById(id).orElseThrow();
    }

    public Profile update(Long id, Profile updated) {
        Profile p = getById(id);
        p.setFullName(updated.getFullName());
        p.setEmail(updated.getEmail());
        p.setDepartment(updated.getDepartment());
        return repository.save(p);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    
    public void generateBatch(List<Profile> profiles) {
        for (Profile profile : profiles) {
            // In a real app, we might save them first or just process them
            if (profile.getId() == null) {
                repository.save(profile);
            }
        }
    }

}