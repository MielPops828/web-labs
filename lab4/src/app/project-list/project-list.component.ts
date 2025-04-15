import { Component, OnInit } from '@angular/core';
import { CommonModule, NgFor } from '@angular/common';
import { RouterLink } from '@angular/router';
import { ProjectService } from '../project.service';
import { TaskService } from '../task.service';
import { ProjectCardComponent } from '../project-card/project-card.component';
import { forkJoin } from 'rxjs';
import { FormsModule } from '@angular/forms';
import { Project } from '../project';

@Component({
  selector: 'app-project-list',
  imports: [FormsModule, RouterLink, ProjectCardComponent, NgFor],
  templateUrl: './project-list.component.html',
  styleUrl: './project-list.component.css'
})

export class ProjectListComponent implements OnInit {
  projects: Project[] = [];
  filteredProjects: Project[] = [];
  projectTaskCounts: { [key: number]: number } = {};

  constructor(
    private projectService: ProjectService,
    private taskService: TaskService
  ) { }

  ngOnInit() {
    forkJoin({
      projects: this.projectService.getAll(),
      taskCounts: this.projectService.getUncompletedTaskCounts()
    }).subscribe({
      next: ({ projects, taskCounts }) => {
        this.projects = projects;
        this.filteredProjects = projects;
        this.projectTaskCounts = taskCounts;
      },
      error: (err) => console.error('Error loading data:', err)
    });
  }

  filterProjects(event: any) {
    const search = event.target.value.toLowerCase();
    this.filteredProjects = this.projects.filter(p =>
      p.name.toLowerCase().includes(search) ||
      p.description.toLowerCase().includes(search)
    );
  }
}
