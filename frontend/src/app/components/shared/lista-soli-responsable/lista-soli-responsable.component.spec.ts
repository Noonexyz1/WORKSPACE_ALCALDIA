import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListaSoliResponsableComponent } from './lista-soli-responsable.component';

describe('ListaSoliResponsableComponent', () => {
  let component: ListaSoliResponsableComponent;
  let fixture: ComponentFixture<ListaSoliResponsableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ListaSoliResponsableComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ListaSoliResponsableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
