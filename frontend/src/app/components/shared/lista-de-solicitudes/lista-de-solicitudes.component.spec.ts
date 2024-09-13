import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListaDeSolicitudesComponent } from './lista-de-solicitudes.component';

describe('ListaDeSolicitudesComponent', () => {
  let component: ListaDeSolicitudesComponent;
  let fixture: ComponentFixture<ListaDeSolicitudesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ListaDeSolicitudesComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ListaDeSolicitudesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
