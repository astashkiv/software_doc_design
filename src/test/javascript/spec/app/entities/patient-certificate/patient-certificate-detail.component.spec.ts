import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PharmacyTestModule } from '../../../test.module';
import { PatientCertificateDetailComponent } from 'app/entities/patient-certificate/patient-certificate-detail.component';
import { PatientCertificate } from 'app/shared/model/patient-certificate.model';

describe('Component Tests', () => {
  describe('PatientCertificate Management Detail Component', () => {
    let comp: PatientCertificateDetailComponent;
    let fixture: ComponentFixture<PatientCertificateDetailComponent>;
    const route = ({ data: of({ patientCertificate: new PatientCertificate(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PharmacyTestModule],
        declarations: [PatientCertificateDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PatientCertificateDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PatientCertificateDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load patientCertificate on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.patientCertificate).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
