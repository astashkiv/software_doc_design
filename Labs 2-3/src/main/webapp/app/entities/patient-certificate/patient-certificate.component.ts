import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPatientCertificate } from 'app/shared/model/patient-certificate.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { PatientCertificateService } from './patient-certificate.service';
import { PatientCertificateDeleteDialogComponent } from './patient-certificate-delete-dialog.component';

@Component({
  selector: 'jhi-patient-certificate',
  templateUrl: './patient-certificate.component.html'
})
export class PatientCertificateComponent implements OnInit, OnDestroy {
  patientCertificates: IPatientCertificate[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected patientCertificateService: PatientCertificateService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.patientCertificates = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.patientCertificateService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IPatientCertificate[]>) => this.paginatePatientCertificates(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.patientCertificates = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInPatientCertificates();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IPatientCertificate): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInPatientCertificates(): void {
    this.eventSubscriber = this.eventManager.subscribe('patientCertificateListModification', () => this.reset());
  }

  delete(patientCertificate: IPatientCertificate): void {
    const modalRef = this.modalService.open(PatientCertificateDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.patientCertificate = patientCertificate;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginatePatientCertificates(data: IPatientCertificate[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.patientCertificates.push(data[i]);
      }
    }
  }
}
