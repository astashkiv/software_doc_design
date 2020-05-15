import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { MedicalConditionService } from 'app/entities/medical-condition/medical-condition.service';
import { IMedicalCondition, MedicalCondition } from 'app/shared/model/medical-condition.model';

describe('Service Tests', () => {
  describe('MedicalCondition Service', () => {
    let injector: TestBed;
    let service: MedicalConditionService;
    let httpMock: HttpTestingController;
    let elemDefault: IMedicalCondition;
    let expectedResult: IMedicalCondition | IMedicalCondition[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(MedicalConditionService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new MedicalCondition(0, 0, 0, 0, 'AAAAAAA', 0, 0, 'AAAAAAA', 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a MedicalCondition', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new MedicalCondition()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a MedicalCondition', () => {
        const returnedFromService = Object.assign(
          {
            age: 1,
            height: 1,
            weight: 1,
            comments: 'BBBBBB',
            temperature: 1,
            bloodSugarLevel: 1,
            pressure: 'BBBBBB',
            pulse: 1
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of MedicalCondition', () => {
        const returnedFromService = Object.assign(
          {
            age: 1,
            height: 1,
            weight: 1,
            comments: 'BBBBBB',
            temperature: 1,
            bloodSugarLevel: 1,
            pressure: 'BBBBBB',
            pulse: 1
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a MedicalCondition', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
