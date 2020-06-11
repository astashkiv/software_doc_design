import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPatientCertificate } from 'app/shared/model/patient-certificate.model';
import { PatientCertificateService } from './patient-certificate.service';

@Component({
  templateUrl: './patient-certificate-delete-dialog.component.html'
})
export class PatientCertificateDeleteDialogComponent {
  patientCertificate?: IPatientCertificate;

  constructor(
    protected patientCertificateService: PatientCertificateService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.patientCertificateService.delete(id).subscribe(() => {
      this.eventManager.broadcast('patientCertificateListModification');
      this.activeModal.close();
    });
  }
}
