package com.ssau.lab3.entity;

import java.time.LocalDate;

import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private LocalDate plannedEndDate;
    @Column(name = "is_completed")
    private boolean isCompleted;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;
}
