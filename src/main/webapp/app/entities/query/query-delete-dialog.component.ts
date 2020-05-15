import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IQuery } from 'app/shared/model/query.model';
import { QueryService } from './query.service';

@Component({
  templateUrl: './query-delete-dialog.component.html'
})
export class QueryDeleteDialogComponent {
  query?: IQuery;

  constructor(protected queryService: QueryService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.queryService.delete(id).subscribe(() => {
      this.eventManager.broadcast('queryListModification');
      this.activeModal.close();
    });
  }
}
