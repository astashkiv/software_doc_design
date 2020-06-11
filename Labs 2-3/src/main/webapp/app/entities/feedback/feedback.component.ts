import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IFeedback } from 'app/shared/model/feedback.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { FeedbackService } from './feedback.service';
import { FeedbackDeleteDialogComponent } from './feedback-delete-dialog.component';

@Component({
  selector: 'jhi-feedback',
  templateUrl: './feedback.component.html'
})
export class FeedbackComponent implements OnInit, OnDestroy {
  feedbacks: IFeedback[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected feedbackService: FeedbackService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.feedbacks = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.feedbackService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IFeedback[]>) => this.paginateFeedbacks(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.feedbacks = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInFeedbacks();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IFeedback): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInFeedbacks(): void {
    this.eventSubscriber = this.eventManager.subscribe('feedbackListModification', () => this.reset());
  }

  delete(feedback: IFeedback): void {
    const modalRef = this.modalService.open(FeedbackDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.feedback = feedback;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateFeedbacks(data: IFeedback[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.feedbacks.push(data[i]);
      }
    }
  }
}
