import { IDoctor } from 'app/shared/model/doctor.model';

export interface IAdmin {
  id?: number;
  username?: string;
  password?: string;
  doctors?: IDoctor[];
}

export class Admin implements IAdmin {
  constructor(public id?: number, public username?: string, public password?: string, public doctors?: IDoctor[]) {}
}
