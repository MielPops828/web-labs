package com.ssau.todo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssau.todo.entity.Project;
import com.ssau.todo.service.ProjectService;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {
    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping ("/hello")
    public String hello(){
        return "hello";
    }

    @GetMapping("/search")
    public ResponseEntity<List<Project>> search (@RequestParam String param){
        System.out.println(param);
        return ResponseEntity.status(418).body(null);
    }
//---------------------------------------------------------------------------------------------------------------------------------------
    @GetMapping("/all") //+
    public ResponseEntity<List<Project>> getAllProjects() {
        return ResponseEntity.ok(projectService.getAllProjects());
    }

    @PostMapping //+
    public ResponseEntity<Void> createProject(@RequestBody Project project) {
        projectService.createProject(project);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}") //+
    public ResponseEntity<Project> getProjectById(@PathVariable Long id){
        Project project = projectService.getProjectById(id);
        return project != null ? new ResponseEntity<>(project, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND); 
    }

    @PutMapping("/update/{id}") //+
    public ResponseEntity<Void> updateProject(@PathVariable Long id, @RequestBody Project project){
        projectService.updateProject(id, project);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")  //+
    public ResponseEntity<Void> deleteProject(@PathVariable Long id){
        projectService.deleteProject(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // GET /find?start_date={start_date}&end_date={end_date}        find?startDate=2025-05-01&endDate=2025-12-31        +
    @GetMapping("/find")
    public ResponseEntity<List<Project>> findProjectByDateRange (@RequestParam LocalDate startDate, @RequestParam LocalDate endDate){
        return new ResponseEntity<>(projectService.findProjectsByDateRange(startDate, endDate), HttpStatus.OK);
    }
}