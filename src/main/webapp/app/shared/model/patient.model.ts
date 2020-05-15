import { IFeedback } from 'app/shared/model/feedback.model';
import { IPrescription } from 'app/shared/model/prescription.model';
import { IPatientCertificate } from 'app/shared/model/patient-certificate.model';
import { IQuery } from 'app/shared/model/query.model';
import { IBooking } from 'app/shared/model/booking.model';
import { IMedicalCondition } from 'app/shared/model/medical-condition.model';
import { IPayment } from 'app/shared/model/payment.model';

export interface IPatient {
  id?: number;
  name?: string;
  phone?: string;
  address?: string;
  feedbacks?: IFeedback[];
  prescriptions?: IPrescription[];
  certificates?: IPatientCertificate[];
  queries?: IQuery[];
  bookings?: IBooking[];
  conditions?: IMedicalCondition[];
  payments?: IPayment[];
}

export class Patient implements IPatient {
  constructor(
    public id?: number,
    public name?: string,
    public phone?: string,
    public address?: string,
    public feedbacks?: IFeedback[],
    public prescriptions?: IPrescription[],
    public certificates?: IPatientCertificate[],
    public queries?: IQuery[],
    public bookings?: IBooking[],
    public conditions?: IMedicalCondition[],
    public payments?: IPayment[]
  ) {}
}
