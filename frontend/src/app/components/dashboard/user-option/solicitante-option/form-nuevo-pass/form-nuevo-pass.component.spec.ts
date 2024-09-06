import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FormNuevoPassComponent } from './form-nuevo-pass.component';

describe('FormNuevoPassComponent', () => {
  let component: FormNuevoPassComponent;
  let fixture: ComponentFixture<FormNuevoPassComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FormNuevoPassComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FormNuevoPassComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
