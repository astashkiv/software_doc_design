import { IMedicine } from 'app/shared/model/medicine.model';

export interface ILanguage {
  id?: number;
  translation?: string;
  medicines?: IMedicine[];
}

export class Language implements ILanguage {
  constructor(public id?: number, public translation?: string, public medicines?: IMedicine[]) {}
}
