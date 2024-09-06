import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InfoSolicitudRespoComponent } from './info-solicitud-respo.component';

describe('InfoSolicitudRespoComponent', () => {
  let component: InfoSolicitudRespoComponent;
  let fixture: ComponentFixture<InfoSolicitudRespoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [InfoSolicitudRespoComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(InfoSolicitudRespoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
