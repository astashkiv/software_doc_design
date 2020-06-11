import { IFeedback } from 'app/shared/model/feedback.model';
import { IDoctorCertificate } from 'app/shared/model/doctor-certificate.model';
import { IPayment } from 'app/shared/model/payment.model';
import { IAdmin } from 'app/shared/model/admin.model';
import { IDepartment } from 'app/shared/model/department.model';

export interface IDoctor {
  id?: number;
  name?: string;
  phone?: string;
  address?: string;
  feedbacks?: IFeedback[];
  certificates?: IDoctorCertificate[];
  payments?: IPayment[];
  admin?: IAdmin;
  department?: IDepartment;
}

export class Doctor implements IDoctor {
  constructor(
    public id?: number,
    public name?: string,
    public phone?: string,
    public address?: string,
    public feedbacks?: IFeedback[],
    public certificates?: IDoctorCertificate[],
    public payments?: IPayment[],
    public admin?: IAdmin,
    public department?: IDepartment
  ) {}
}
