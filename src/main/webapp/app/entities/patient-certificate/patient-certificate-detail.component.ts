import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPatientCertificate } from 'app/shared/model/patient-certificate.model';

@Component({
  selector: 'jhi-patient-certificate-detail',
  templateUrl: './patient-certificate-detail.component.html'
})
export class PatientCertificateDetailComponent implements OnInit {
  patientCertificate: IPatientCertificate | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ patientCertificate }) => (this.patientCertificate = patientCertificate));
  }

  previousState(): void {
    window.history.back();
  }
}
