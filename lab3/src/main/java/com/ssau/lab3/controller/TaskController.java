package com.ssau.lab3.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssau.lab3.DTO.TaskDTO;
import com.ssau.lab3.service.TaskService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/projects/{projectId}/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService){
        this.taskService = taskService;
    }

    @GetMapping
    public ResponseEntity<List<TaskDTO>> getTasks(@PathVariable Long projectId) {
        return ResponseEntity.ok(taskService.findAllTasksByProjectId(projectId));
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<TaskDTO> getTask(
        @PathVariable Long projectId,
        @PathVariable Long taskId
    ) {
        return ResponseEntity.ok(taskService.findTaskById(projectId, taskId));
    }

    @PostMapping
    public ResponseEntity<TaskDTO> createTask(
        @PathVariable Long projectId,
        @RequestBody TaskDTO taskDTO
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(taskService.createTask(projectId, taskDTO));
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<TaskDTO> updateTask(
        @PathVariable Long projectId,
        @PathVariable Long taskId,
        @RequestBody TaskDTO taskDTO
    ) {
        return ResponseEntity.ok(taskService.updateTask(projectId, taskId, taskDTO));
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(
        @PathVariable Long taskId,
        @PathVariable Long projectId
    ) {
        taskService.deleteTask(taskId, projectId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteCompletedTasks(@PathVariable Long projectId) {
        taskService.deleteCompletedTasks(projectId);
        return ResponseEntity.noContent().build();
    }
}
