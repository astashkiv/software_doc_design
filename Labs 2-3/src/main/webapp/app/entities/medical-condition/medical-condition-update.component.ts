import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IMedicalCondition, MedicalCondition } from 'app/shared/model/medical-condition.model';
import { MedicalConditionService } from './medical-condition.service';
import { IPatient } from 'app/shared/model/patient.model';
import { PatientService } from 'app/entities/patient/patient.service';

@Component({
  selector: 'jhi-medical-condition-update',
  templateUrl: './medical-condition-update.component.html'
})
export class MedicalConditionUpdateComponent implements OnInit {
  isSaving = false;
  patients: IPatient[] = [];

  editForm = this.fb.group({
    id: [],
    age: [],
    height: [],
    weight: [],
    comments: [],
    temperature: [],
    bloodSugarLevel: [],
    pressure: [],
    pulse: [],
    patient: []
  });

  constructor(
    protected medicalConditionService: MedicalConditionService,
    protected patientService: PatientService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ medicalCondition }) => {
      this.updateForm(medicalCondition);

      this.patientService.query().subscribe((res: HttpResponse<IPatient[]>) => (this.patients = res.body || []));
    });
  }

  updateForm(medicalCondition: IMedicalCondition): void {
    this.editForm.patchValue({
      id: medicalCondition.id,
      age: medicalCondition.age,
      height: medicalCondition.height,
      weight: medicalCondition.weight,
      comments: medicalCondition.comments,
      temperature: medicalCondition.temperature,
      bloodSugarLevel: medicalCondition.bloodSugarLevel,
      pressure: medicalCondition.pressure,
      pulse: medicalCondition.pulse,
      patient: medicalCondition.patient
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const medicalCondition = this.createFromForm();
    if (medicalCondition.id !== undefined) {
      this.subscribeToSaveResponse(this.medicalConditionService.update(medicalCondition));
    } else {
      this.subscribeToSaveResponse(this.medicalConditionService.create(medicalCondition));
    }
  }

  private createFromForm(): IMedicalCondition {
    return {
      ...new MedicalCondition(),
      id: this.editForm.get(['id'])!.value,
      age: this.editForm.get(['age'])!.value,
      height: this.editForm.get(['height'])!.value,
      weight: this.editForm.get(['weight'])!.value,
      comments: this.editForm.get(['comments'])!.value,
      temperature: this.editForm.get(['temperature'])!.value,
      bloodSugarLevel: this.editForm.get(['bloodSugarLevel'])!.value,
      pressure: this.editForm.get(['pressure'])!.value,
      pulse: this.editForm.get(['pulse'])!.value,
      patient: this.editForm.get(['patient'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMedicalCondition>>): void {
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

  trackById(index: number, item: IPatient): any {
    return item.id;
  }
}
