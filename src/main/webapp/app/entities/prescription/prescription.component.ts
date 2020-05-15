import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPrescription } from 'app/shared/model/prescription.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { PrescriptionService } from './prescription.service';
import { PrescriptionDeleteDialogComponent } from './prescription-delete-dialog.component';

@Component({
  selector: 'jhi-prescription',
  templateUrl: './prescription.component.html'
})
export class PrescriptionComponent implements OnInit, OnDestroy {
  prescriptions: IPrescription[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected prescriptionService: PrescriptionService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.prescriptions = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.prescriptionService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IPrescription[]>) => this.paginatePrescriptions(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.prescriptions = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInPrescriptions();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IPrescription): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInPrescriptions(): void {
    this.eventSubscriber = this.eventManager.subscribe('prescriptionListModification', () => this.reset());
  }

  delete(prescription: IPrescription): void {
    const modalRef = this.modalService.open(PrescriptionDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.prescription = prescription;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginatePrescriptions(data: IPrescription[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.prescriptions.push(data[i]);
      }
    }
  }
}
