import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IPatientCertificate, PatientCertificate } from 'app/shared/model/patient-certificate.model';
import { PatientCertificateService } from './patient-certificate.service';
import { IPatient } from 'app/shared/model/patient.model';
import { PatientService } from 'app/entities/patient/patient.service';

@Component({
  selector: 'jhi-patient-certificate-update',
  templateUrl: './patient-certificate-update.component.html'
})
export class PatientCertificateUpdateComponent implements OnInit {
  isSaving = false;
  patients: IPatient[] = [];
  receivedDateDp: any;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    receivedDate: [],
    doctor: []
  });

  constructor(
    protected patientCertificateService: PatientCertificateService,
    protected patientService: PatientService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ patientCertificate }) => {
      this.updateForm(patientCertificate);

      this.patientService.query().subscribe((res: HttpResponse<IPatient[]>) => (this.patients = res.body || []));
    });
  }

  updateForm(patientCertificate: IPatientCertificate): void {
    this.editForm.patchValue({
      id: patientCertificate.id,
      name: patientCertificate.name,
      receivedDate: patientCertificate.receivedDate,
      doctor: patientCertificate.doctor
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const patientCertificate = this.createFromForm();
    if (patientCertificate.id !== undefined) {
      this.subscribeToSaveResponse(this.patientCertificateService.update(patientCertificate));
    } else {
      this.subscribeToSaveResponse(this.patientCertificateService.create(patientCertificate));
    }
  }

  private createFromForm(): IPatientCertificate {
    return {
      ...new PatientCertificate(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      receivedDate: this.editForm.get(['receivedDate'])!.value,
      doctor: this.editForm.get(['doctor'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPatientCertificate>>): void {
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
