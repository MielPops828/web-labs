package com.ssau.lab3.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssau.lab3.DTO.ProjectDTO;
import com.ssau.lab3.service.ProjectService;

@RestController
@RequestMapping("/projects")
public class ProjectController {
    private final ProjectService projectService;

    public ProjectController (ProjectService projectService){
        this.projectService = projectService;
    } 

    @GetMapping("/uncompleted-tasks")
    public ResponseEntity<Map<Long, Long>> getUncompletedTaskCounts() {
        return ResponseEntity.ok(projectService.getUncompletedTaskCounts());
    }
    
    @GetMapping
    public ResponseEntity<List<ProjectDTO>> getProjects(@RequestParam(required = false) String search) {
        return ResponseEntity.ok(projectService.findAllProjects(search));
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<ProjectDTO> getProject(@PathVariable Long projectId) {
        return ResponseEntity.ok(projectService.findProjectById(projectId));
    }

    @PostMapping
    public ResponseEntity<ProjectDTO> createProject(@RequestBody ProjectDTO projectDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(projectService.createProject(projectDTO));
    }

    @PutMapping("/{projectId}")
    public ResponseEntity<ProjectDTO> updateProject(@PathVariable Long projectId, @RequestBody ProjectDTO projectDTO){
        return ResponseEntity.ok(projectService.updateProject(projectId, projectDTO));
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long projectId) {
        projectService.deleteProject(projectId);
        return ResponseEntity.noContent().build();
    }
}
