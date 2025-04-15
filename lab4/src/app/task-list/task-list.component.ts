import { Component, OnInit } from '@angular/core';
import { CommonModule, NgFor } from '@angular/common';
import { Task } from '../task';
import { ActivatedRoute, ActivatedRouteSnapshot } from '@angular/router';
import { TaskService } from '../task.service';
import { TaskCardComponent } from '../task-card/task-card.component';

@Component({
  selector: 'app-task-list',
  imports: [NgFor, TaskCardComponent, CommonModule],
  templateUrl: './task-list.component.html',
  styleUrl: './task-list.component.css'
})
export class TaskListComponent implements OnInit {
  tasks: Task[] = [];
  projectId!: number;

  constructor(
    private route: ActivatedRoute,
    private taskService: TaskService
  ) {}

  ngOnInit() {
    this.projectId = +this.route.snapshot.paramMap.get('projectId')!;
    this.loadTasks();
  }

  loadTasks() {
    this.taskService.getTasksByProject(this.projectId)
      .subscribe(tasks => this.tasks = tasks);
  }

  deleteTask(taskId: number) {
    this.taskService.deleteTask(this.projectId, taskId)
      .subscribe({
        next: () => this.loadTasks(),
        error: (err) => console.error('Error deleting task:', err)
      });
  }
}
