import { Moment } from 'moment';
import { IPatient } from 'app/shared/model/patient.model';

export interface IPatientCertificate {
  id?: number;
  name?: string;
  receivedDate?: Moment;
  doctor?: IPatient;
}

export class PatientCertificate implements IPatientCertificate {
  constructor(public id?: number, public name?: string, public receivedDate?: Moment, public doctor?: IPatient) {}
}
