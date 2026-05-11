package com.iot.dashboard.controller;

import com.iot.dashboard.dto.TemplateDto;
import com.iot.dashboard.model.Template;
import com.iot.dashboard.repository.UserRepository;
import com.iot.dashboard.service.TemplateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/templates")
public class TemplateController {

    private final TemplateService service;
    private final UserRepository userRepository;

    public TemplateController(TemplateService service, UserRepository userRepository) {
        this.service = service;
        this.userRepository = userRepository;
    }

    // ✅ Create or update template
    @PostMapping
    public ResponseEntity<Template> saveTemplate(@RequestBody TemplateDto dto, Principal principal) {
        Long userId = getUserIdFromPrincipal(principal);
        Template saved = service.saveTemplateWithWidgets(dto, userId);
        return ResponseEntity.ok(saved);
    }

    // ✅ Get all templates of user
    @GetMapping
    public List<Template> list(Principal principal) {
        Long userId = getUserIdFromPrincipal(principal);
        return service.getTemplatesByUser(userId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTemplate(@PathVariable String id, Principal principal) {
        Long userId = getUserIdFromPrincipal(principal);
        service.deleteTemplate(id, userId);
        return ResponseEntity.noContent().build(); // 204 No Content
    }


    // ✅ Get single template
    @GetMapping("/{templateId}")
    public ResponseEntity<Template> getTemplate(@PathVariable String templateId, Principal principal) {
        Long userId = getUserIdFromPrincipal(principal);
        return ResponseEntity.ok(service.getTemplateById(templateId, userId));
    }

    // Helper to get userId from JWT principal
    private Long getUserIdFromPrincipal(Principal principal) {
        String principalName = principal.getName();
        return userRepository.findByUsername(principalName)
                .or(() -> userRepository.findByEmail(principalName))
                .orElseThrow(() -> new RuntimeException("User not found"))
                .getId();
    }
}
