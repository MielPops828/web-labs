import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';
import { environment } from './environment';
import { Task } from './task';

@Injectable({ providedIn: 'root' })
export class TaskService {
  private apiUrl = `${environment.apiUrl}`;

  constructor(private http: HttpClient) { }

  getTasksByProject(projectId: number): Observable<Task[]> {
    return this.http.get<Task[]>(`${this.apiUrl}/projects/${projectId}/tasks`).pipe(
      map(tasks => tasks.map(t => ({
        ...t,
        plannedEndDate: new Date(t.plannedEndDate),
        isCompleted: t.completed ?? t.completed
      })))
    );
  }

  getTask(projectId: number, taskId: number): Observable<Task> {
    return this.http.get<Task>(`${this.apiUrl}/projects/${projectId}/tasks/${taskId}`).pipe(
      map(t => ({
        ...t,
        plannedEndDate: new Date(t.plannedEndDate),
        isCompleted: t.completed ?? t.completed
      }))
    );
  }

  deleteTask(projectId: number, taskId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/projects/${projectId}/tasks/${taskId}`);
  }
}