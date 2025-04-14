package com.ssau.todo.entity;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Task {
    private Long id;
    private String name;
    private String description;
    private LocalDate plannedEndDate;
    private boolean isCompleted;
    private Long projectId;
}
