import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { PharmacyTestModule } from '../../../test.module';
import { DoctorCertificateUpdateComponent } from 'app/entities/doctor-certificate/doctor-certificate-update.component';
import { DoctorCertificateService } from 'app/entities/doctor-certificate/doctor-certificate.service';
import { DoctorCertificate } from 'app/shared/model/doctor-certificate.model';

describe('Component Tests', () => {
  describe('DoctorCertificate Management Update Component', () => {
    let comp: DoctorCertificateUpdateComponent;
    let fixture: ComponentFixture<DoctorCertificateUpdateComponent>;
    let service: DoctorCertificateService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PharmacyTestModule],
        declarations: [DoctorCertificateUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(DoctorCertificateUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DoctorCertificateUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DoctorCertificateService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new DoctorCertificate(123);
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
        const entity = new DoctorCertificate();
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
