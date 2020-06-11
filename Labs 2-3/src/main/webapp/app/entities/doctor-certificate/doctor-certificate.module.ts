import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PharmacySharedModule } from 'app/shared/shared.module';
import { DoctorCertificateComponent } from './doctor-certificate.component';
import { DoctorCertificateDetailComponent } from './doctor-certificate-detail.component';
import { DoctorCertificateUpdateComponent } from './doctor-certificate-update.component';
import { DoctorCertificateDeleteDialogComponent } from './doctor-certificate-delete-dialog.component';
import { doctorCertificateRoute } from './doctor-certificate.route';

@NgModule({
  imports: [PharmacySharedModule, RouterModule.forChild(doctorCertificateRoute)],
  declarations: [
    DoctorCertificateComponent,
    DoctorCertificateDetailComponent,
    DoctorCertificateUpdateComponent,
    DoctorCertificateDeleteDialogComponent
  ],
  entryComponents: [DoctorCertificateDeleteDialogComponent]
})
export class PharmacyDoctorCertificateModule {}
