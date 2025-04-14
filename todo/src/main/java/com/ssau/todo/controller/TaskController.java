package com.ssau.todo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssau.todo.entity.Task;
import com.ssau.todo.service.TaskService;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    @Autowired
    private TaskService taskService;

    public TaskController(TaskService taskService){
        this.taskService = taskService;
    }
    
    @PostMapping    //+
    public ResponseEntity<Void> createTask(@RequestBody Task task){ 
        taskService.createTask(task);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/update/{id}")     //+
    public ResponseEntity<Void> updateTask(@PathVariable Long id, @RequestBody Task task){
        taskService.updateTask(id, task);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")  //+
    public ResponseEntity<Void> deleteTask(@PathVariable Long id){
        taskService.deleteTask(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/get/{id}")    //+
    public ResponseEntity<Task> getTaskById(@PathVariable Long id){
        Task task = taskService.getTaskById(id);
        return task!= null ? new ResponseEntity<>(task, HttpStatus.OK): new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{projectId}")     //+
    public ResponseEntity<List<Task>> getTaskByProjectId(@PathVariable Long projectId){
        return new ResponseEntity<>(taskService.getTaskByProjectId(projectId), HttpStatus.OK);
    }

    @GetMapping("/all") //+
    public ResponseEntity<List<Task>> getAllTasks(){
        return new ResponseEntity<>(taskService.getAllTasks(), HttpStatus.OK);
    }
}
