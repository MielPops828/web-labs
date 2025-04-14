package com.ssau.todo.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ssau.todo.entity.Project;
import com.ssau.todo.repository.ProjectRepository;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public List<Project> getAllProjects() {
        return projectRepository.getAllProjects();
    }

    public Project getProjectById(Long id) {
        return projectRepository.selectById(id);
    }

    public void createProject(Project project) {
        projectRepository.saveProject(project);
    }

    public void updateProject(Long id, Project project) {
        projectRepository.updateProject(id, project);
    }

    public void deleteProject(Long id) {
        projectRepository.deleteProject(id);
    }

    public List<Project> findProjectsByDateRange(LocalDate startDate, LocalDate endDate) {
        return projectRepository.findByDateRange(startDate, endDate);
    }
}
