import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PharmacyTestModule } from '../../../test.module';
import { DoctorCertificateDetailComponent } from 'app/entities/doctor-certificate/doctor-certificate-detail.component';
import { DoctorCertificate } from 'app/shared/model/doctor-certificate.model';

describe('Component Tests', () => {
  describe('DoctorCertificate Management Detail Component', () => {
    let comp: DoctorCertificateDetailComponent;
    let fixture: ComponentFixture<DoctorCertificateDetailComponent>;
    const route = ({ data: of({ doctorCertificate: new DoctorCertificate(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PharmacyTestModule],
        declarations: [DoctorCertificateDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(DoctorCertificateDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DoctorCertificateDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load doctorCertificate on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.doctorCertificate).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
