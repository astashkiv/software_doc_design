import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IMedicalCondition, MedicalCondition } from 'app/shared/model/medical-condition.model';
import { MedicalConditionService } from './medical-condition.service';
import { MedicalConditionComponent } from './medical-condition.component';
import { MedicalConditionDetailComponent } from './medical-condition-detail.component';
import { MedicalConditionUpdateComponent } from './medical-condition-update.component';

@Injectable({ providedIn: 'root' })
export class MedicalConditionResolve implements Resolve<IMedicalCondition> {
  constructor(private service: MedicalConditionService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMedicalCondition> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((medicalCondition: HttpResponse<MedicalCondition>) => {
          if (medicalCondition.body) {
            return of(medicalCondition.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new MedicalCondition());
  }
}

export const medicalConditionRoute: Routes = [
  {
    path: '',
    component: MedicalConditionComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'MedicalConditions'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: MedicalConditionDetailComponent,
    resolve: {
      medicalCondition: MedicalConditionResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'MedicalConditions'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: MedicalConditionUpdateComponent,
    resolve: {
      medicalCondition: MedicalConditionResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'MedicalConditions'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: MedicalConditionUpdateComponent,
    resolve: {
      medicalCondition: MedicalConditionResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'MedicalConditions'
    },
    canActivate: [UserRouteAccessService]
  }
];
