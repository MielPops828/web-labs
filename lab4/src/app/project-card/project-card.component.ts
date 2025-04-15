import { Component, Input } from '@angular/core';
import { Project } from '../project';
import { Router, RouterLink, RouterModule } from '@angular/router';
import { CommonModule, DatePipe } from '@angular/common';

@Component({
  selector: 'app-project-card',
  imports: [DatePipe, RouterModule, RouterLink, CommonModule],
  templateUrl: './project-card.component.html',
  styleUrl: './project-card.component.css'
})
export class ProjectCardComponent {

  @Input() project!: Project;
  @Input() openTasksCount!: number;
}
