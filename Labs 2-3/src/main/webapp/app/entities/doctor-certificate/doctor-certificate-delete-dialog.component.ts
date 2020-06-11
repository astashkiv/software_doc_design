import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDoctorCertificate } from 'app/shared/model/doctor-certificate.model';
import { DoctorCertificateService } from './doctor-certificate.service';

@Component({
  templateUrl: './doctor-certificate-delete-dialog.component.html'
})
export class DoctorCertificateDeleteDialogComponent {
  doctorCertificate?: IDoctorCertificate;

  constructor(
    protected doctorCertificateService: DoctorCertificateService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.doctorCertificateService.delete(id).subscribe(() => {
      this.eventManager.broadcast('doctorCertificateListModification');
      this.activeModal.close();
    });
  }
}
