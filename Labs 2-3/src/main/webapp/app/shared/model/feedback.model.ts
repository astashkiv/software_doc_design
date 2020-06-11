import { IDoctor } from 'app/shared/model/doctor.model';
import { IPatient } from 'app/shared/model/patient.model';

export interface IFeedback {
  id?: number;
  doctor?: IDoctor;
  patient?: IPatient;
}

export class Feedback implements IFeedback {
  constructor(public id?: number, public doctor?: IDoctor, public patient?: IPatient) {}
}
