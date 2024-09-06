import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FormNuevaSolicitudComponent } from './form-nueva-solicitud.component';

describe('FormNuevaSolicitudComponent', () => {
  let component: FormNuevaSolicitudComponent;
  let fixture: ComponentFixture<FormNuevaSolicitudComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FormNuevaSolicitudComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FormNuevaSolicitudComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
