import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListaSoliOperadorComponent } from './lista-soli-operador.component';

describe('ListaSoliOperadorComponent', () => {
  let component: ListaSoliOperadorComponent;
  let fixture: ComponentFixture<ListaSoliOperadorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ListaSoliOperadorComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ListaSoliOperadorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
