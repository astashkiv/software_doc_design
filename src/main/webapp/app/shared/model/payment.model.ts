import { Moment } from 'moment';
import { IDoctor } from 'app/shared/model/doctor.model';
import { IPatient } from 'app/shared/model/patient.model';

export interface IPayment {
  id?: number;
  invoiceCode?: string;
  date?: Moment;
  amount?: number;
  doctor?: IDoctor;
  patient?: IPatient;
}

export class Payment implements IPayment {
  constructor(
    public id?: number,
    public invoiceCode?: string,
    public date?: Moment,
    public amount?: number,
    public doctor?: IDoctor,
    public patient?: IPatient
  ) {}
}
