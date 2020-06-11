import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PharmacySharedModule } from 'app/shared/shared.module';
import { QueryComponent } from './query.component';
import { QueryDetailComponent } from './query-detail.component';
import { QueryUpdateComponent } from './query-update.component';
import { QueryDeleteDialogComponent } from './query-delete-dialog.component';
import { queryRoute } from './query.route';

@NgModule({
  imports: [PharmacySharedModule, RouterModule.forChild(queryRoute)],
  declarations: [QueryComponent, QueryDetailComponent, QueryUpdateComponent, QueryDeleteDialogComponent],
  entryComponents: [QueryDeleteDialogComponent]
})
export class PharmacyQueryModule {}
