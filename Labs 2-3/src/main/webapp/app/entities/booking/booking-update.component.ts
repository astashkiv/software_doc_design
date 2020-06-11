import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IBooking, Booking } from 'app/shared/model/booking.model';
import { BookingService } from './booking.service';
import { IDoctor } from 'app/shared/model/doctor.model';
import { DoctorService } from 'app/entities/doctor/doctor.service';
import { IPatient } from 'app/shared/model/patient.model';
import { PatientService } from 'app/entities/patient/patient.service';

type SelectableEntity = IDoctor | IPatient;

@Component({
  selector: 'jhi-booking-update',
  templateUrl: './booking-update.component.html'
})
export class BookingUpdateComponent implements OnInit {
  isSaving = false;
  towhos: IDoctor[] = [];
  patients: IPatient[] = [];

  editForm = this.fb.group({
    id: [],
    when: [],
    toWho: [],
    byWho: []
  });

  constructor(
    protected bookingService: BookingService,
    protected doctorService: DoctorService,
    protected patientService: PatientService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ booking }) => {
      if (!booking.id) {
        const today = moment().startOf('day');
        booking.when = today;
      }

      this.updateForm(booking);

      this.doctorService
        .query({ 'bookingId.specified': 'false' })
        .pipe(
          map((res: HttpResponse<IDoctor[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IDoctor[]) => {
          if (!booking.toWho || !booking.toWho.id) {
            this.towhos = resBody;
          } else {
            this.doctorService
              .find(booking.toWho.id)
              .pipe(
                map((subRes: HttpResponse<IDoctor>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IDoctor[]) => (this.towhos = concatRes));
          }
        });

      this.patientService.query().subscribe((res: HttpResponse<IPatient[]>) => (this.patients = res.body || []));
    });
  }

  updateForm(booking: IBooking): void {
    this.editForm.patchValue({
      id: booking.id,
      when: booking.when ? booking.when.format(DATE_TIME_FORMAT) : null,
      toWho: booking.toWho,
      byWho: booking.byWho
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const booking = this.createFromForm();
    if (booking.id !== undefined) {
      this.subscribeToSaveResponse(this.bookingService.update(booking));
    } else {
      this.subscribeToSaveResponse(this.bookingService.create(booking));
    }
  }

  private createFromForm(): IBooking {
    return {
      ...new Booking(),
      id: this.editForm.get(['id'])!.value,
      when: this.editForm.get(['when'])!.value ? moment(this.editForm.get(['when'])!.value, DATE_TIME_FORMAT) : undefined,
      toWho: this.editForm.get(['toWho'])!.value,
      byWho: this.editForm.get(['byWho'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBooking>>): void {
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
}
