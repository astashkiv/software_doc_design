import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IQuery } from 'app/shared/model/query.model';

type EntityResponseType = HttpResponse<IQuery>;
type EntityArrayResponseType = HttpResponse<IQuery[]>;

@Injectable({ providedIn: 'root' })
export class QueryService {
  public resourceUrl = SERVER_API_URL + 'api/queries';

  constructor(protected http: HttpClient) {}

  create(query: IQuery): Observable<EntityResponseType> {
    return this.http.post<IQuery>(this.resourceUrl, query, { observe: 'response' });
  }

  update(query: IQuery): Observable<EntityResponseType> {
    return this.http.put<IQuery>(this.resourceUrl, query, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IQuery>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IQuery[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
