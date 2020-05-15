import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IMedicine, Medicine } from 'app/shared/model/medicine.model';
import { MedicineService } from './medicine.service';
import { ILanguage } from 'app/shared/model/language.model';
import { LanguageService } from 'app/entities/language/language.service';

@Component({
  selector: 'jhi-medicine-update',
  templateUrl: './medicine-update.component.html'
})
export class MedicineUpdateComponent implements OnInit {
  isSaving = false;
  languages: ILanguage[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    instruction: [],
    languagesIns: []
  });

  constructor(
    protected medicineService: MedicineService,
    protected languageService: LanguageService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ medicine }) => {
      this.updateForm(medicine);

      this.languageService.query().subscribe((res: HttpResponse<ILanguage[]>) => (this.languages = res.body || []));
    });
  }

  updateForm(medicine: IMedicine): void {
    this.editForm.patchValue({
      id: medicine.id,
      name: medicine.name,
      instruction: medicine.instruction,
      languagesIns: medicine.languagesIns
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const medicine = this.createFromForm();
    if (medicine.id !== undefined) {
      this.subscribeToSaveResponse(this.medicineService.update(medicine));
    } else {
      this.subscribeToSaveResponse(this.medicineService.create(medicine));
    }
  }

  private createFromForm(): IMedicine {
    return {
      ...new Medicine(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      instruction: this.editForm.get(['instruction'])!.value,
      languagesIns: this.editForm.get(['languagesIns'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMedicine>>): void {
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

  trackById(index: number, item: ILanguage): any {
    return item.id;
  }

  getSelected(selectedVals: ILanguage[], option: ILanguage): ILanguage {
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
