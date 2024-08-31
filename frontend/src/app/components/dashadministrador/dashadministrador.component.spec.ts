import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DashadministradorComponent } from './dashadministrador.component';

describe('DashadministradorComponent', () => {
  let component: DashadministradorComponent;
  let fixture: ComponentFixture<DashadministradorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DashadministradorComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DashadministradorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
