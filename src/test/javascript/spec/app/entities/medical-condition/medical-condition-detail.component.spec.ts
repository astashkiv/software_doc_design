import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PharmacyTestModule } from '../../../test.module';
import { MedicalConditionDetailComponent } from 'app/entities/medical-condition/medical-condition-detail.component';
import { MedicalCondition } from 'app/shared/model/medical-condition.model';

describe('Component Tests', () => {
  describe('MedicalCondition Management Detail Component', () => {
    let comp: MedicalConditionDetailComponent;
    let fixture: ComponentFixture<MedicalConditionDetailComponent>;
    const route = ({ data: of({ medicalCondition: new MedicalCondition(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PharmacyTestModule],
        declarations: [MedicalConditionDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(MedicalConditionDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MedicalConditionDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load medicalCondition on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.medicalCondition).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
