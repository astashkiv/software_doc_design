import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PharmacySharedModule } from 'app/shared/shared.module';
import { MedicalConditionComponent } from './medical-condition.component';
import { MedicalConditionDetailComponent } from './medical-condition-detail.component';
import { MedicalConditionUpdateComponent } from './medical-condition-update.component';
import { MedicalConditionDeleteDialogComponent } from './medical-condition-delete-dialog.component';
import { medicalConditionRoute } from './medical-condition.route';

@NgModule({
  imports: [PharmacySharedModule, RouterModule.forChild(medicalConditionRoute)],
  declarations: [
    MedicalConditionComponent,
    MedicalConditionDetailComponent,
    MedicalConditionUpdateComponent,
    MedicalConditionDeleteDialogComponent
  ],
  entryComponents: [MedicalConditionDeleteDialogComponent]
})
export class PharmacyMedicalConditionModule {}
