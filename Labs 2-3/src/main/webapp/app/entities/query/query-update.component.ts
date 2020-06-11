import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IQuery, Query } from 'app/shared/model/query.model';
import { QueryService } from './query.service';
import { IDoctor } from 'app/shared/model/doctor.model';
import { DoctorService } from 'app/entities/doctor/doctor.service';
import { IPatient } from 'app/shared/model/patient.model';
import { PatientService } from 'app/entities/patient/patient.service';

type SelectableEntity = IDoctor | IPatient;

@Component({
  selector: 'jhi-query-update',
  templateUrl: './query-update.component.html'
})
export class QueryUpdateComponent implements OnInit {
  isSaving = false;
  answeredbies: IDoctor[] = [];
  patients: IPatient[] = [];

  editForm = this.fb.group({
    id: [],
    query: [null, [Validators.required]],
    answer: [],
    answeredBy: [],
    askedBy: []
  });

  constructor(
    protected queryService: QueryService,
    protected doctorService: DoctorService,
    protected patientService: PatientService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ query }) => {
      this.updateForm(query);

      this.doctorService
        .query({ 'queryId.specified': 'false' })
        .pipe(
          map((res: HttpResponse<IDoctor[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IDoctor[]) => {
          if (!query.answeredBy || !query.answeredBy.id) {
            this.answeredbies = resBody;
          } else {
            this.doctorService
              .find(query.answeredBy.id)
              .pipe(
                map((subRes: HttpResponse<IDoctor>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IDoctor[]) => (this.answeredbies = concatRes));
          }
        });

      this.patientService.query().subscribe((res: HttpResponse<IPatient[]>) => (this.patients = res.body || []));
    });
  }

  updateForm(query: IQuery): void {
    this.editForm.patchValue({
      id: query.id,
      query: query.query,
      answer: query.answer,
      answeredBy: query.answeredBy,
      askedBy: query.askedBy
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const query = this.createFromForm();
    if (query.id !== undefined) {
      this.subscribeToSaveResponse(this.queryService.update(query));
    } else {
      this.subscribeToSaveResponse(this.queryService.create(query));
    }
  }

  private createFromForm(): IQuery {
    return {
      ...new Query(),
      id: this.editForm.get(['id'])!.value,
      query: this.editForm.get(['query'])!.value,
      answer: this.editForm.get(['answer'])!.value,
      answeredBy: this.editForm.get(['answeredBy'])!.value,
      askedBy: this.editForm.get(['askedBy'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IQuery>>): void {
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
