import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IQuery, Query } from 'app/shared/model/query.model';
import { QueryService } from './query.service';
import { QueryComponent } from './query.component';
import { QueryDetailComponent } from './query-detail.component';
import { QueryUpdateComponent } from './query-update.component';

@Injectable({ providedIn: 'root' })
export class QueryResolve implements Resolve<IQuery> {
  constructor(private service: QueryService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IQuery> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((query: HttpResponse<Query>) => {
          if (query.body) {
            return of(query.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Query());
  }
}

export const queryRoute: Routes = [
  {
    path: '',
    component: QueryComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Queries'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: QueryDetailComponent,
    resolve: {
      query: QueryResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Queries'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: QueryUpdateComponent,
    resolve: {
      query: QueryResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Queries'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: QueryUpdateComponent,
    resolve: {
      query: QueryResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Queries'
    },
    canActivate: [UserRouteAccessService]
  }
];
