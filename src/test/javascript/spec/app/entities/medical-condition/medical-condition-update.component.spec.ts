import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { PharmacyTestModule } from '../../../test.module';
import { MedicalConditionUpdateComponent } from 'app/entities/medical-condition/medical-condition-update.component';
import { MedicalConditionService } from 'app/entities/medical-condition/medical-condition.service';
import { MedicalCondition } from 'app/shared/model/medical-condition.model';

describe('Component Tests', () => {
  describe('MedicalCondition Management Update Component', () => {
    let comp: MedicalConditionUpdateComponent;
    let fixture: ComponentFixture<MedicalConditionUpdateComponent>;
    let service: MedicalConditionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PharmacyTestModule],
        declarations: [MedicalConditionUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(MedicalConditionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MedicalConditionUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MedicalConditionService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new MedicalCondition(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new MedicalCondition();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
