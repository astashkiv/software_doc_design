import { IDoctor } from 'app/shared/model/doctor.model';

export interface IDepartment {
  id?: number;
  name?: string;
  doctors?: IDoctor[];
}

export class Department implements IDepartment {
  constructor(public id?: number, public name?: string, public doctors?: IDoctor[]) {}
}
