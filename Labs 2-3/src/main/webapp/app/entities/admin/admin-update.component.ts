import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IAdmin, Admin } from 'app/shared/model/admin.model';
import { AdminService } from './admin.service';

@Component({
  selector: 'jhi-admin-update',
  templateUrl: './admin-update.component.html'
})
export class AdminUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    username: [null, [Validators.required, Validators.maxLength(100)]],
    password: [null, [Validators.required]]
  });

  constructor(protected adminService: AdminService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ admin }) => {
      this.updateForm(admin);
    });
  }

  updateForm(admin: IAdmin): void {
    this.editForm.patchValue({
      id: admin.id,
      username: admin.username,
      password: admin.password
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const admin = this.createFromForm();
    if (admin.id !== undefined) {
      this.subscribeToSaveResponse(this.adminService.update(admin));
    } else {
      this.subscribeToSaveResponse(this.adminService.create(admin));
    }
  }

  private createFromForm(): IAdmin {
    return {
      ...new Admin(),
      id: this.editForm.get(['id'])!.value,
      username: this.editForm.get(['username'])!.value,
      password: this.editForm.get(['password'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAdmin>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
