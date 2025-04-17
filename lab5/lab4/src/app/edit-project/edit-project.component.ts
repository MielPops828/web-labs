import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { ProjectService } from '../project.service';
import { CommonModule } from '@angular/common';


@Component({
  selector: 'app-edit-project',
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './edit-project.component.html',
  styleUrl: './edit-project.component.css'
})
export class EditProjectComponent implements OnInit {
  projectForm: FormGroup;
  projectId?: number;

  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private projectService: ProjectService
  ) {
    this.projectForm = this.fb.group({
      name: [''],
      description: [''],
      startDate: [''],
      endDate: ['']
    });
  }

  ngOnInit() {
    this.projectId = +this.route.snapshot.paramMap.get('projectId')!;
    if (this.projectId) {
      this.projectService.getProjectById(this.projectId)
        .subscribe(project => this.projectForm.patchValue(project));
    }
  }

  saveProject() {
    const project = { ...this.projectForm.value, id: this.projectId };
    const operation = this.projectId 
      ? this.projectService.updateProject(project)
      : this.projectService.createProject(project);
    
    operation.subscribe(() => this.router.navigate(['/projects']));
  }
}
