import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PharmacyTestModule } from '../../../test.module';
import { QueryDetailComponent } from 'app/entities/query/query-detail.component';
import { Query } from 'app/shared/model/query.model';

describe('Component Tests', () => {
  describe('Query Management Detail Component', () => {
    let comp: QueryDetailComponent;
    let fixture: ComponentFixture<QueryDetailComponent>;
    const route = ({ data: of({ query: new Query(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PharmacyTestModule],
        declarations: [QueryDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(QueryDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(QueryDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load query on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.query).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
