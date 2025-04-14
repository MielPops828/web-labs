package com.ssau.todo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssau.todo.entity.Task;
import com.ssau.todo.repository.TaskRepository;
@Service
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService (TaskRepository taskRepository){
        this.taskRepository = taskRepository;
    }

    public List<Task> getAllTasks() {
        return taskRepository.getAllTasks();
    }

    public Task getTaskById(Long id) {
        return taskRepository.selectById(id);
    }

    public void createTask(Task task) {
        taskRepository.saveTask(task);
    }

    public void updateTask(Long id, Task task) {
        taskRepository.updateTask(id, task);
    }

    public void deleteTask(Long id) {
        taskRepository.deleteTask(id);
    }

    public List<Task> getTaskByProjectId(Long projectId) {
        return taskRepository.findByProjectId(projectId);
    } 
}
