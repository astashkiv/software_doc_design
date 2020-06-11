import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PharmacySharedModule } from 'app/shared/shared.module';
import { PatientCertificateComponent } from './patient-certificate.component';
import { PatientCertificateDetailComponent } from './patient-certificate-detail.component';
import { PatientCertificateUpdateComponent } from './patient-certificate-update.component';
import { PatientCertificateDeleteDialogComponent } from './patient-certificate-delete-dialog.component';
import { patientCertificateRoute } from './patient-certificate.route';

@NgModule({
  imports: [PharmacySharedModule, RouterModule.forChild(patientCertificateRoute)],
  declarations: [
    PatientCertificateComponent,
    PatientCertificateDetailComponent,
    PatientCertificateUpdateComponent,
    PatientCertificateDeleteDialogComponent
  ],
  entryComponents: [PatientCertificateDeleteDialogComponent]
})
export class PharmacyPatientCertificateModule {}
