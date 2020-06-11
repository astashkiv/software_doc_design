import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IDoctorCertificate } from 'app/shared/model/doctor-certificate.model';

type EntityResponseType = HttpResponse<IDoctorCertificate>;
type EntityArrayResponseType = HttpResponse<IDoctorCertificate[]>;

@Injectable({ providedIn: 'root' })
export class DoctorCertificateService {
  public resourceUrl = SERVER_API_URL + 'api/doctor-certificates';

  constructor(protected http: HttpClient) {}

  create(doctorCertificate: IDoctorCertificate): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(doctorCertificate);
    return this.http
      .post<IDoctorCertificate>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(doctorCertificate: IDoctorCertificate): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(doctorCertificate);
    return this.http
      .put<IDoctorCertificate>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IDoctorCertificate>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDoctorCertificate[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(doctorCertificate: IDoctorCertificate): IDoctorCertificate {
    const copy: IDoctorCertificate = Object.assign({}, doctorCertificate, {
      receivedDate:
        doctorCertificate.receivedDate && doctorCertificate.receivedDate.isValid()
          ? doctorCertificate.receivedDate.format(DATE_FORMAT)
          : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.receivedDate = res.body.receivedDate ? moment(res.body.receivedDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((doctorCertificate: IDoctorCertificate) => {
        doctorCertificate.receivedDate = doctorCertificate.receivedDate ? moment(doctorCertificate.receivedDate) : undefined;
      });
    }
    return res;
  }
}
