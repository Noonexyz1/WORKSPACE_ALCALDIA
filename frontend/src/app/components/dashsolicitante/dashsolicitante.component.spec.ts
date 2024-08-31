import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DashsolicitanteComponent } from './dashsolicitante.component';

describe('DashsolicitanteComponent', () => {
  let component: DashsolicitanteComponent;
  let fixture: ComponentFixture<DashsolicitanteComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DashsolicitanteComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DashsolicitanteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
