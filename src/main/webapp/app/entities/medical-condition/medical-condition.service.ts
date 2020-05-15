import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IMedicalCondition } from 'app/shared/model/medical-condition.model';

type EntityResponseType = HttpResponse<IMedicalCondition>;
type EntityArrayResponseType = HttpResponse<IMedicalCondition[]>;

@Injectable({ providedIn: 'root' })
export class MedicalConditionService {
  public resourceUrl = SERVER_API_URL + 'api/medical-conditions';

  constructor(protected http: HttpClient) {}

  create(medicalCondition: IMedicalCondition): Observable<EntityResponseType> {
    return this.http.post<IMedicalCondition>(this.resourceUrl, medicalCondition, { observe: 'response' });
  }

  update(medicalCondition: IMedicalCondition): Observable<EntityResponseType> {
    return this.http.put<IMedicalCondition>(this.resourceUrl, medicalCondition, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IMedicalCondition>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMedicalCondition[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
