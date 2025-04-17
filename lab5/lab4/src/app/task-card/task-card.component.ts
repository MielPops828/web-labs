import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Task } from '../task';
import { CommonModule, DatePipe } from '@angular/common';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'app-task-card',
  standalone: true,
  imports: [
    DatePipe,
    MatCheckboxModule,
    MatIconModule,
    MatButtonModule,
    CommonModule
  ],
  templateUrl: './task-card.component.html',
  styleUrl: './task-card.component.css'
})
export class TaskCardComponent {
  @Input() task!: Task;
  @Output() delete = new EventEmitter<number>();
}