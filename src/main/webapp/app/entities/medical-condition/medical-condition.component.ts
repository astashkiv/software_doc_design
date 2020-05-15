import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IMedicalCondition } from 'app/shared/model/medical-condition.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { MedicalConditionService } from './medical-condition.service';
import { MedicalConditionDeleteDialogComponent } from './medical-condition-delete-dialog.component';

@Component({
  selector: 'jhi-medical-condition',
  templateUrl: './medical-condition.component.html'
})
export class MedicalConditionComponent implements OnInit, OnDestroy {
  medicalConditions: IMedicalCondition[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected medicalConditionService: MedicalConditionService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.medicalConditions = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.medicalConditionService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IMedicalCondition[]>) => this.paginateMedicalConditions(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.medicalConditions = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInMedicalConditions();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IMedicalCondition): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInMedicalConditions(): void {
    this.eventSubscriber = this.eventManager.subscribe('medicalConditionListModification', () => this.reset());
  }

  delete(medicalCondition: IMedicalCondition): void {
    const modalRef = this.modalService.open(MedicalConditionDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.medicalCondition = medicalCondition;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateMedicalConditions(data: IMedicalCondition[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.medicalConditions.push(data[i]);
      }
    }
  }
}
