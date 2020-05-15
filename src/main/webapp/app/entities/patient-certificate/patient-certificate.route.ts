import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPatientCertificate, PatientCertificate } from 'app/shared/model/patient-certificate.model';
import { PatientCertificateService } from './patient-certificate.service';
import { PatientCertificateComponent } from './patient-certificate.component';
import { PatientCertificateDetailComponent } from './patient-certificate-detail.component';
import { PatientCertificateUpdateComponent } from './patient-certificate-update.component';

@Injectable({ providedIn: 'root' })
export class PatientCertificateResolve implements Resolve<IPatientCertificate> {
  constructor(private service: PatientCertificateService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPatientCertificate> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((patientCertificate: HttpResponse<PatientCertificate>) => {
          if (patientCertificate.body) {
            return of(patientCertificate.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PatientCertificate());
  }
}

export const patientCertificateRoute: Routes = [
  {
    path: '',
    component: PatientCertificateComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'PatientCertificates'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PatientCertificateDetailComponent,
    resolve: {
      patientCertificate: PatientCertificateResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'PatientCertificates'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PatientCertificateUpdateComponent,
    resolve: {
      patientCertificate: PatientCertificateResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'PatientCertificates'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PatientCertificateUpdateComponent,
    resolve: {
      patientCertificate: PatientCertificateResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'PatientCertificates'
    },
    canActivate: [UserRouteAccessService]
  }
];
