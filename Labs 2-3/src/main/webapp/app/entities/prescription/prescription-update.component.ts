import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IPrescription, Prescription } from 'app/shared/model/prescription.model';
import { PrescriptionService } from './prescription.service';
import { IDoctor } from 'app/shared/model/doctor.model';
import { DoctorService } from 'app/entities/doctor/doctor.service';
import { IMedicine } from 'app/shared/model/medicine.model';
import { MedicineService } from 'app/entities/medicine/medicine.service';
import { IPatient } from 'app/shared/model/patient.model';
import { PatientService } from 'app/entities/patient/patient.service';

type SelectableEntity = IDoctor | IMedicine | IPatient;

@Component({
  selector: 'jhi-prescription-update',
  templateUrl: './prescription-update.component.html'
})
export class PrescriptionUpdateComponent implements OnInit {
  isSaving = false;
  signedbies: IDoctor[] = [];
  medicines: IMedicine[] = [];
  patients: IPatient[] = [];

  editForm = this.fb.group({
    id: [],
    name: [],
    timings: [],
    medicineCount: [],
    signedBy: [],
    medicines: [],
    patient: []
  });

  constructor(
    protected prescriptionService: PrescriptionService,
    protected doctorService: DoctorService,
    protected medicineService: MedicineService,
    protected patientService: PatientService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ prescription }) => {
      this.updateForm(prescription);

      this.doctorService
        .query({ 'prescriptionId.specified': 'false' })
        .pipe(
          map((res: HttpResponse<IDoctor[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IDoctor[]) => {
          if (!prescription.signedBy || !prescription.signedBy.id) {
            this.signedbies = resBody;
          } else {
            this.doctorService
              .find(prescription.signedBy.id)
              .pipe(
                map((subRes: HttpResponse<IDoctor>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IDoctor[]) => (this.signedbies = concatRes));
          }
        });

      this.medicineService.query().subscribe((res: HttpResponse<IMedicine[]>) => (this.medicines = res.body || []));

      this.patientService.query().subscribe((res: HttpResponse<IPatient[]>) => (this.patients = res.body || []));
    });
  }

  updateForm(prescription: IPrescription): void {
    this.editForm.patchValue({
      id: prescription.id,
      name: prescription.name,
      timings: prescription.timings,
      medicineCount: prescription.medicineCount,
      signedBy: prescription.signedBy,
      medicines: prescription.medicines,
      patient: prescription.patient
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const prescription = this.createFromForm();
    if (prescription.id !== undefined) {
      this.subscribeToSaveResponse(this.prescriptionService.update(prescription));
    } else {
      this.subscribeToSaveResponse(this.prescriptionService.create(prescription));
    }
  }

  private createFromForm(): IPrescription {
    return {
      ...new Prescription(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      timings: this.editForm.get(['timings'])!.value,
      medicineCount: this.editForm.get(['medicineCount'])!.value,
      signedBy: this.editForm.get(['signedBy'])!.value,
      medicines: this.editForm.get(['medicines'])!.value,
      patient: this.editForm.get(['patient'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPrescription>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }

  getSelected(selectedVals: IMedicine[], option: IMedicine): IMedicine {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
