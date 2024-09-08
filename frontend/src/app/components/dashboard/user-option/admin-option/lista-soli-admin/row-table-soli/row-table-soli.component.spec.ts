import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RowTableSoliComponent } from './row-table-soli.component';

describe('RowTableSoliComponent', () => {
  let component: RowTableSoliComponent;
  let fixture: ComponentFixture<RowTableSoliComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RowTableSoliComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RowTableSoliComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
