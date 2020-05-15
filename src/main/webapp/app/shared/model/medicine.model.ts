import { ILanguage } from 'app/shared/model/language.model';
import { IPrescription } from 'app/shared/model/prescription.model';

export interface IMedicine {
  id?: number;
  name?: string;
  instruction?: string;
  languagesIns?: ILanguage[];
  prescriptions?: IPrescription[];
}

export class Medicine implements IMedicine {
  constructor(
    public id?: number,
    public name?: string,
    public instruction?: string,
    public languagesIns?: ILanguage[],
    public prescriptions?: IPrescription[]
  ) {}
}
