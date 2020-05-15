import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDoctorCertificate } from 'app/shared/model/doctor-certificate.model';

@Component({
  selector: 'jhi-doctor-certificate-detail',
  templateUrl: './doctor-certificate-detail.component.html'
})
export class DoctorCertificateDetailComponent implements OnInit {
  doctorCertificate: IDoctorCertificate | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ doctorCertificate }) => (this.doctorCertificate = doctorCertificate));
  }

  previousState(): void {
    window.history.back();
  }
}
