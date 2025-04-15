import { Injectable } from '@angular/core';
import { environment } from './environment';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Project } from './project';
import { forkJoin, map, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ProjectService {
  private apiUrl = `${environment.apiUrl}/projects`;

  constructor(private http: HttpClient) {}

  getAll(): Observable<Project[]>{
    return this.http.get<Project[]>(this.apiUrl)
  }

  getProjectById(id: number): Observable<Project>{
    return this.http.get<Project>(`${this.apiUrl}/${id}`)
  }

  createProject(project: Omit<Project, 'id'>): Observable<Project>{
    return this.http.post<Project>(this.apiUrl, project)
  }

  updateProject(project: Project): Observable<Project>{
    return this.http.put<Project>(`${this.apiUrl}/${project.id}`, project);
  }

  getUncompletedTaskCounts(): Observable<{[key: number]: number}>{
    return this.http.get<{[key: number]: number}>(`${this.apiUrl}/uncompleted-tasks`)
  }
}
