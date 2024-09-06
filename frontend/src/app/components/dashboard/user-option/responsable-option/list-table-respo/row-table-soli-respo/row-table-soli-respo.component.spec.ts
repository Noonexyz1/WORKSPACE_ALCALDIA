import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RowTableSoliRespoComponent } from './row-table-soli-respo.component';

describe('RowTableSoliRespoComponent', () => {
  let component: RowTableSoliRespoComponent;
  let fixture: ComponentFixture<RowTableSoliRespoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RowTableSoliRespoComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RowTableSoliRespoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
