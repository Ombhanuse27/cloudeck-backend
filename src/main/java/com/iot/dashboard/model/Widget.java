package com.iot.dashboard.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;


@Entity
@Table(name = "widgets")
public class Widget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String widgetType;
    private String name;
    private int positionX;
    private int positionY;
    private String config;

    @ManyToOne
    @JoinColumn(name = "template_id")
    @JsonBackReference
    private Template template;

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getWidgetType() { return widgetType; }
    public void setWidgetType(String widgetType) { this.widgetType = widgetType; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getPositionX() { return positionX; }
    public void setPositionX(int positionX) { this.positionX = positionX; }
    public int getPositionY() { return positionY; }
    public void setPositionY(int positionY) { this.positionY = positionY; }
    public String getConfig() { return config; }
    public void setConfig(String config) { this.config = config; }
    public Template getTemplate() { return template; }
    public void setTemplate(Template template) { this.template = template; }
}