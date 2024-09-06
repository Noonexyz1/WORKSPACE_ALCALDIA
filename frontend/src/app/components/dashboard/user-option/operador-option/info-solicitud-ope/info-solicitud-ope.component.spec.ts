import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InfoSolicitudOpeComponent } from './info-solicitud-ope.component';

describe('InfoSolicitudOpeComponent', () => {
  let component: InfoSolicitudOpeComponent;
  let fixture: ComponentFixture<InfoSolicitudOpeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [InfoSolicitudOpeComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(InfoSolicitudOpeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
