import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SolicitanteOptionComponent } from './solicitante-option.component';

describe('SolicitanteOptionComponent', () => {
  let component: SolicitanteOptionComponent;
  let fixture: ComponentFixture<SolicitanteOptionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SolicitanteOptionComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SolicitanteOptionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
