package com.example.demo.idcard.repository;

import com.example.demo.idcard.model.Template;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TemplateRepository extends JpaRepository<Template, Long> {

    boolean existsByName(String name);
}
