package com.iot.dashboard.repository;

import com.iot.dashboard.model.Template;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TemplateRepository extends JpaRepository<Template, Long> {
    List<Template> findByUserId(Long userId);
    Optional<Template> findByTemplateId(String templateId);
}
