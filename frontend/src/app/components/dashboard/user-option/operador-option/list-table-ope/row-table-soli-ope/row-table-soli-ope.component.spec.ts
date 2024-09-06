import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RowTableSoliOpeComponent } from './row-table-soli-ope.component';

describe('RowTableSoliOpeComponent', () => {
  let component: RowTableSoliOpeComponent;
  let fixture: ComponentFixture<RowTableSoliOpeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RowTableSoliOpeComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RowTableSoliOpeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
