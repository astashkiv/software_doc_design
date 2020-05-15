import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'doctor',
        loadChildren: () => import('./doctor/doctor.module').then(m => m.PharmacyDoctorModule)
      },
      {
        path: 'patient',
        loadChildren: () => import('./patient/patient.module').then(m => m.PharmacyPatientModule)
      },
      {
        path: 'booking',
        loadChildren: () => import('./booking/booking.module').then(m => m.PharmacyBookingModule)
      },
      {
        path: 'admin',
        loadChildren: () => import('./admin/admin.module').then(m => m.PharmacyAdminModule)
      },
      {
        path: 'department',
        loadChildren: () => import('./department/department.module').then(m => m.PharmacyDepartmentModule)
      },
      {
        path: 'query',
        loadChildren: () => import('./query/query.module').then(m => m.PharmacyQueryModule)
      },
      {
        path: 'feedback',
        loadChildren: () => import('./feedback/feedback.module').then(m => m.PharmacyFeedbackModule)
      },
      {
        path: 'medicine',
        loadChildren: () => import('./medicine/medicine.module').then(m => m.PharmacyMedicineModule)
      },
      {
        path: 'language',
        loadChildren: () => import('./language/language.module').then(m => m.PharmacyLanguageModule)
      },
      {
        path: 'doctor-certificate',
        loadChildren: () => import('./doctor-certificate/doctor-certificate.module').then(m => m.PharmacyDoctorCertificateModule)
      },
      {
        path: 'patient-certificate',
        loadChildren: () => import('./patient-certificate/patient-certificate.module').then(m => m.PharmacyPatientCertificateModule)
      },
      {
        path: 'prescription',
        loadChildren: () => import('./prescription/prescription.module').then(m => m.PharmacyPrescriptionModule)
      },
      {
        path: 'medical-condition',
        loadChildren: () => import('./medical-condition/medical-condition.module').then(m => m.PharmacyMedicalConditionModule)
      },
      {
        path: 'payment',
        loadChildren: () => import('./payment/payment.module').then(m => m.PharmacyPaymentModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class PharmacyEntityModule {}
