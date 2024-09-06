import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RowTableSoliSoliComponent } from './row-table-soli-soli.component';

describe('RowTableSoliSoliComponent', () => {
  let component: RowTableSoliSoliComponent;
  let fixture: ComponentFixture<RowTableSoliSoliComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RowTableSoliSoliComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RowTableSoliSoliComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
