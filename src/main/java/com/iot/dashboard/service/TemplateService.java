package com.iot.dashboard.service;

import com.iot.dashboard.dto.TemplateDto;
import com.iot.dashboard.model.Template;
import com.iot.dashboard.model.User;
import com.iot.dashboard.repository.TemplateRepository;
import com.iot.dashboard.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class TemplateService {

    @Autowired
    private TemplateRepository templateRepository;

    @Autowired
    private UserRepository userRepository;

    // Save or update template with automatic templateId & authToken
    @Transactional
    public Template saveTemplateWithWidgets(TemplateDto dto, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id " + userId));

        Template template = dto.getTemplateId() != null
                ? templateRepository.findByTemplateId(dto.getTemplateId()).orElse(new Template())
                : new Template();

        template.setName(dto.getName());
        template.setDescription(dto.getDescription());
        template.setLayoutJson(dto.getLayoutJson() != null ? dto.getLayoutJson() : "{}");
        template.setUserId(userId);
        template.setCreatedAt(LocalDateTime.now());

        // ✅ Generate templateId if null
        if (template.getTemplateId() == null) {
            template.setTemplateId(UUID.randomUUID().toString());
        }

        // ✅ Generate authToken if null
        if (template.getAuthToken() == null) {
            template.setAuthToken(UUID.randomUUID().toString().replace("-", ""));
        }

        return templateRepository.save(template);
    }

    public List<Template> getTemplatesByUser(Long userId) {
        return templateRepository.findByUserId(userId);
    }

    public Template getTemplateById(String templateId, Long userId) {
        Template template = templateRepository.findByTemplateId(templateId)
                .orElseThrow(() -> new RuntimeException("Template not found"));

        if (!template.getUserId().equals(userId)) {
            throw new RuntimeException("Unauthorized: template does not belong to this user");
        }

        return template;
    }

    // ✅ New delete method
    @Transactional
    public void deleteTemplate(String templateId, Long userId) {
        Template template = templateRepository.findByTemplateId(templateId)
                .orElseThrow(() -> new RuntimeException("Template not found"));

        if (!template.getUserId().equals(userId)) {
            throw new RuntimeException("Unauthorized: template does not belong to this user");
        }

        templateRepository.delete(template);
    }
}
