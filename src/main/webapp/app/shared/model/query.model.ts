import { IDoctor } from 'app/shared/model/doctor.model';
import { IPatient } from 'app/shared/model/patient.model';

export interface IQuery {
  id?: number;
  query?: string;
  answer?: string;
  answeredBy?: IDoctor;
  askedBy?: IPatient;
}

export class Query implements IQuery {
  constructor(public id?: number, public query?: string, public answer?: string, public answeredBy?: IDoctor, public askedBy?: IPatient) {}
}
