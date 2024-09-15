import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RowTableSolicitudesComponent } from './row-table-solicitudes.component';

describe('RowTableSolicitudesComponent', () => {
  let component: RowTableSolicitudesComponent;
  let fixture: ComponentFixture<RowTableSolicitudesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RowTableSolicitudesComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RowTableSolicitudesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
