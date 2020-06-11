import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMedicalCondition } from 'app/shared/model/medical-condition.model';
import { MedicalConditionService } from './medical-condition.service';

@Component({
  templateUrl: './medical-condition-delete-dialog.component.html'
})
export class MedicalConditionDeleteDialogComponent {
  medicalCondition?: IMedicalCondition;

  constructor(
    protected medicalConditionService: MedicalConditionService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.medicalConditionService.delete(id).subscribe(() => {
      this.eventManager.broadcast('medicalConditionListModification');
      this.activeModal.close();
    });
  }
}
