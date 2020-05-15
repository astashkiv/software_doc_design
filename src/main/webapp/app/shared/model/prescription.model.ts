import { IDoctor } from 'app/shared/model/doctor.model';
import { IMedicine } from 'app/shared/model/medicine.model';
import { IPatient } from 'app/shared/model/patient.model';

export interface IPrescription {
  id?: number;
  name?: string;
  timings?: string;
  medicineCount?: number;
  signedBy?: IDoctor;
  medicines?: IMedicine[];
  patient?: IPatient;
}

export class Prescription implements IPrescription {
  constructor(
    public id?: number,
    public name?: string,
    public timings?: string,
    public medicineCount?: number,
    public signedBy?: IDoctor,
    public medicines?: IMedicine[],
    public patient?: IPatient
  ) {}
}
