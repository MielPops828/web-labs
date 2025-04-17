package com.ssau.lab2.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.ssau.lab2.TaskNotFoundException;
import com.ssau.lab2.DTO.TaskDTO;
import com.ssau.lab2.entity.Project;
import com.ssau.lab2.entity.Task;
import com.ssau.lab2.repository.ProjectRepository;
import com.ssau.lab2.repository.TaskRepository;


@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;

    public TaskService(TaskRepository taskRepository, ProjectRepository projectRepository){
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
    }

    public List<TaskDTO> findAllTasksByProjectId(Long projectId) {
        return taskRepository.findByProjectId(projectId).stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public TaskDTO findTaskById(Long projectId, Long taskId) {
        Task task = taskRepository.findByIdAndProjectId(taskId, projectId).orElseThrow(() -> new TaskNotFoundException("This task is not found"));
        return convertToDTO(task);
    }

    public TaskDTO createTask(Long projectId, TaskDTO taskDTO) {
    // controlleradvice
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new TaskNotFoundException("This task is not found"));
        Task task = convertToEntity(taskDTO);
        task.setProject(project);
        return convertToDTO(taskRepository.save(task));
    }

    public TaskDTO updateTask(Long projectId, Long taskId, TaskDTO taskDTO) {
        Task existingTask = taskRepository.findByIdAndProjectId(taskId, projectId).orElseThrow(() -> new TaskNotFoundException("This task is not found"));
        
        existingTask.setName(taskDTO.getName());
        existingTask.setDescription(taskDTO.getDescription());
        existingTask.setCompleted(taskDTO.isCompleted());
        existingTask.setPlannedEndDate(taskDTO.getPlannedEndDate());
        
        return convertToDTO(taskRepository.save(existingTask));
    }

    public void deleteTask(Long taskId, Long projectId) {
        taskRepository.deleteByIdAndProjectId(taskId, projectId);
    }

    public void deleteCompletedTasks(Long projectId) {
        taskRepository.deleteCompletedTasksByProjectId(projectId);
    }

    private TaskDTO convertToDTO(Task task) {
        TaskDTO dto = new TaskDTO();
        dto.setId(task.getId());
        dto.setName(task.getName());
        dto.setDescription(task.getDescription());
        dto.setPlannedEndDate(task.getPlannedEndDate());
        dto.setCompleted(task.isCompleted());
        dto.setProjectId(task.getProject().getId());
        return dto;
    }

    private Task convertToEntity(TaskDTO dto) {
        Task task = new Task();
        task.setName(dto.getName());
        task.setDescription(dto.getDescription());
        task.setPlannedEndDate(dto.getPlannedEndDate());
        task.setCompleted(dto.isCompleted());
        return task;
    }
}
