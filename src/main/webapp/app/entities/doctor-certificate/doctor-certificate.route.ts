import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IDoctorCertificate, DoctorCertificate } from 'app/shared/model/doctor-certificate.model';
import { DoctorCertificateService } from './doctor-certificate.service';
import { DoctorCertificateComponent } from './doctor-certificate.component';
import { DoctorCertificateDetailComponent } from './doctor-certificate-detail.component';
import { DoctorCertificateUpdateComponent } from './doctor-certificate-update.component';

@Injectable({ providedIn: 'root' })
export class DoctorCertificateResolve implements Resolve<IDoctorCertificate> {
  constructor(private service: DoctorCertificateService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDoctorCertificate> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((doctorCertificate: HttpResponse<DoctorCertificate>) => {
          if (doctorCertificate.body) {
            return of(doctorCertificate.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DoctorCertificate());
  }
}

export const doctorCertificateRoute: Routes = [
  {
    path: '',
    component: DoctorCertificateComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'DoctorCertificates'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: DoctorCertificateDetailComponent,
    resolve: {
      doctorCertificate: DoctorCertificateResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'DoctorCertificates'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: DoctorCertificateUpdateComponent,
    resolve: {
      doctorCertificate: DoctorCertificateResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'DoctorCertificates'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: DoctorCertificateUpdateComponent,
    resolve: {
      doctorCertificate: DoctorCertificateResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'DoctorCertificates'
    },
    canActivate: [UserRouteAccessService]
  }
];
