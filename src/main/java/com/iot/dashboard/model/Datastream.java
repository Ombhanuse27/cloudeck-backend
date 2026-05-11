package com.iot.dashboard.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@Table(name = "datastreams")
public class Datastream {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String pin; // e.g., V1, V2

    private String type; // INPUT / OUTPUT

    private String value; // latest value

    private String title; // Widget title

    @Column(columnDefinition = "TEXT")
    private String configJson; // widget-specific config like min/max/units

    @Column(nullable = false)
    private String templateId; // link to template
}
