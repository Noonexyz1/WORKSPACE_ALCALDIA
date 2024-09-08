import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FormNuevoPassAdminComponent } from './form-nuevo-pass-admin.component';

describe('FormNuevoPassAdminComponent', () => {
  let component: FormNuevoPassAdminComponent;
  let fixture: ComponentFixture<FormNuevoPassAdminComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FormNuevoPassAdminComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FormNuevoPassAdminComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
