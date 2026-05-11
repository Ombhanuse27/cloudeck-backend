    package com.iot.dashboard.dto;

    import java.util.List;

    public class TemplateDto {
        private Long id;
        private String templateId;
        private String name;
        private String description;
        private String authToken;
        private Long userId;

        // New fields for layout and widgets
        private String layoutJson;
        private List<WidgetDto> widgets;

        // --- Getters & Setters ---
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }

        public String getTemplateId() { return templateId; }
        public void setTemplateId(String templateId) { this.templateId = templateId; }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }

        public String getAuthToken() { return authToken; }
        public void setAuthToken(String authToken) { this.authToken = authToken; }

        public Long getUserId() { return userId; }
        public void setUserId(Long userId) { this.userId = userId; }

        public String getLayoutJson() { return layoutJson; }
        public void setLayoutJson(String layoutJson) { this.layoutJson = layoutJson; }

        public List<WidgetDto> getWidgets() { return widgets; }
        public void setWidgets(List<WidgetDto> widgets) { this.widgets = widgets; }
    }
