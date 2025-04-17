package com.ssau.lab2.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ssau.lab2.DTO.ProjectDTO;
import com.ssau.lab2.entity.Project;

import com.ssau.lab2.repository.ProjectRepository;
import com.ssau.lab2.repository.TaskRepository;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;
    public ProjectService (ProjectRepository projectRepository, TaskRepository taskRepository){
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
    }

    public List<ProjectDTO> findAllProjects(String search) {
        List<Project> projects = search != null ? projectRepository.findBySearchTerm(search) : projectRepository.findAll();
        return projects.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public ProjectDTO findProjectById(Long projectId) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found"));
        return convertToDTO(project);
    }

    public ProjectDTO createProject(ProjectDTO projectDTO) {
        Project project = convertToEntity(projectDTO);
        return convertToDTO(projectRepository.save(project));
    }

    public ProjectDTO updateProject(Long projectId, ProjectDTO projectDTO) {
        Project existingProject = projectRepository.findById(projectId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        existingProject.setName(projectDTO.getName());
        existingProject.setDescription(projectDTO.getDescription());
        existingProject.setStartDate(projectDTO.getStartDate());
        existingProject.setEndDate(projectDTO.getEndDate());
        
        return convertToDTO(projectRepository.save(existingProject));
    }

    public void deleteProject(Long projectId) {
        projectRepository.deleteById(projectId);
    }

    public ProjectDTO convertToDTO(Project project) {
        ProjectDTO dto = new ProjectDTO();
        dto.setId(project.getId());
        dto.setName(project.getName());
        dto.setDescription(project.getDescription());
        dto.setStartDate(project.getStartDate());
        dto.setEndDate(project.getEndDate());
        return dto;
    }

    public Project convertToEntity(ProjectDTO dto) {
        Project project = new Project();
        project.setName(dto.getName());
        project.setDescription(dto.getDescription());
        project.setStartDate(dto.getStartDate());
        project.setEndDate(dto.getEndDate());
        return project;
    }

    public Map<Long, Long> getUncompletedTaskCounts() {
        List<Object[]> results = taskRepository.countUncompletedTasks();
        Map<Long, Long> counts = results.stream()
            .collect(Collectors.toMap(
                arr -> (Long) arr[0], 
                arr -> (Long) arr[1]
            ));
    
        projectRepository.findAll().forEach(project -> {
            counts.putIfAbsent(project.getId(), 0L);
        });
        return counts;
    }
}
