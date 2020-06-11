import { Moment } from 'moment';
import { IDoctor } from 'app/shared/model/doctor.model';

export interface IDoctorCertificate {
  id?: number;
  name?: string;
  receivedDate?: Moment;
  doctor?: IDoctor;
}

export class DoctorCertificate implements IDoctorCertificate {
  constructor(public id?: number, public name?: string, public receivedDate?: Moment, public doctor?: IDoctor) {}
}
