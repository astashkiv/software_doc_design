import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IQuery } from 'app/shared/model/query.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { QueryService } from './query.service';
import { QueryDeleteDialogComponent } from './query-delete-dialog.component';

@Component({
  selector: 'jhi-query',
  templateUrl: './query.component.html'
})
export class QueryComponent implements OnInit, OnDestroy {
  queries: IQuery[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected queryService: QueryService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.queries = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.queryService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IQuery[]>) => this.paginateQueries(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.queries = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInQueries();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IQuery): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInQueries(): void {
    this.eventSubscriber = this.eventManager.subscribe('queryListModification', () => this.reset());
  }

  delete(query: IQuery): void {
    const modalRef = this.modalService.open(QueryDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.query = query;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateQueries(data: IQuery[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.queries.push(data[i]);
      }
    }
  }
}
