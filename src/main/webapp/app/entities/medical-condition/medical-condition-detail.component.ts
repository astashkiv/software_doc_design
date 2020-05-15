import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMedicalCondition } from 'app/shared/model/medical-condition.model';

@Component({
  selector: 'jhi-medical-condition-detail',
  templateUrl: './medical-condition-detail.component.html'
})
export class MedicalConditionDetailComponent implements OnInit {
  medicalCondition: IMedicalCondition | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ medicalCondition }) => (this.medicalCondition = medicalCondition));
  }

  previousState(): void {
    window.history.back();
  }
}
