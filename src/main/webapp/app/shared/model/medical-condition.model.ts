import { IPatient } from 'app/shared/model/patient.model';

export interface IMedicalCondition {
  id?: number;
  age?: number;
  height?: number;
  weight?: number;
  comments?: string;
  temperature?: number;
  bloodSugarLevel?: number;
  pressure?: string;
  pulse?: number;
  patient?: IPatient;
}

export class MedicalCondition implements IMedicalCondition {
  constructor(
    public id?: number,
    public age?: number,
    public height?: number,
    public weight?: number,
    public comments?: string,
    public temperature?: number,
    public bloodSugarLevel?: number,
    public pressure?: string,
    public pulse?: number,
    public patient?: IPatient
  ) {}
}
