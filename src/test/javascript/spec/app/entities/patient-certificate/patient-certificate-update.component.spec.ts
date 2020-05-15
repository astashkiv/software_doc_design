import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { PharmacyTestModule } from '../../../test.module';
import { PatientCertificateUpdateComponent } from 'app/entities/patient-certificate/patient-certificate-update.component';
import { PatientCertificateService } from 'app/entities/patient-certificate/patient-certificate.service';
import { PatientCertificate } from 'app/shared/model/patient-certificate.model';

describe('Component Tests', () => {
  describe('PatientCertificate Management Update Component', () => {
    let comp: PatientCertificateUpdateComponent;
    let fixture: ComponentFixture<PatientCertificateUpdateComponent>;
    let service: PatientCertificateService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PharmacyTestModule],
        declarations: [PatientCertificateUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PatientCertificateUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PatientCertificateUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PatientCertificateService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PatientCertificate(123);
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
        const entity = new PatientCertificate();
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
