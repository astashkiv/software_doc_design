import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IDoctorCertificate, DoctorCertificate } from 'app/shared/model/doctor-certificate.model';
import { DoctorCertificateService } from './doctor-certificate.service';
import { IDoctor } from 'app/shared/model/doctor.model';
import { DoctorService } from 'app/entities/doctor/doctor.service';

@Component({
  selector: 'jhi-doctor-certificate-update',
  templateUrl: './doctor-certificate-update.component.html'
})
export class DoctorCertificateUpdateComponent implements OnInit {
  isSaving = false;
  doctors: IDoctor[] = [];
  receivedDateDp: any;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    receivedDate: [],
    doctor: []
  });

  constructor(
    protected doctorCertificateService: DoctorCertificateService,
    protected doctorService: DoctorService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ doctorCertificate }) => {
      this.updateForm(doctorCertificate);

      this.doctorService.query().subscribe((res: HttpResponse<IDoctor[]>) => (this.doctors = res.body || []));
    });
  }

  updateForm(doctorCertificate: IDoctorCertificate): void {
    this.editForm.patchValue({
      id: doctorCertificate.id,
      name: doctorCertificate.name,
      receivedDate: doctorCertificate.receivedDate,
      doctor: doctorCertificate.doctor
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const doctorCertificate = this.createFromForm();
    if (doctorCertificate.id !== undefined) {
      this.subscribeToSaveResponse(this.doctorCertificateService.update(doctorCertificate));
    } else {
      this.subscribeToSaveResponse(this.doctorCertificateService.create(doctorCertificate));
    }
  }

  private createFromForm(): IDoctorCertificate {
    return {
      ...new DoctorCertificate(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      receivedDate: this.editForm.get(['receivedDate'])!.value,
      doctor: this.editForm.get(['doctor'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDoctorCertificate>>): void {
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

  trackById(index: number, item: IDoctor): any {
    return item.id;
  }
}
