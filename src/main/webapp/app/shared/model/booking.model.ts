import { Moment } from 'moment';
import { IDoctor } from 'app/shared/model/doctor.model';
import { IPatient } from 'app/shared/model/patient.model';

export interface IBooking {
  id?: number;
  when?: Moment;
  toWho?: IDoctor;
  byWho?: IPatient;
}

export class Booking implements IBooking {
  constructor(public id?: number, public when?: Moment, public toWho?: IDoctor, public byWho?: IPatient) {}
}
