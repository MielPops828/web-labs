import { Routes } from '@angular/router';
import { ProjectListComponent } from './project-list/project-list.component';
import { EditProjectComponent } from './edit-project/edit-project.component';
import { TaskListComponent } from './task-list/task-list.component';


export const routes: Routes = [
  { path: 'projects', component: ProjectListComponent },
  { path: 'projects/:projectId', component: EditProjectComponent },
  { path: 'projects/new', component: EditProjectComponent },
  { path: 'tasks/:projectId', component: TaskListComponent },
  { path: '', redirectTo: '/projects', pathMatch: 'full' }
];
