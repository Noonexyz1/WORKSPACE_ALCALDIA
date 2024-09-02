import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OperadorOptionComponent } from './operador-option.component';

describe('OperadorOptionComponent', () => {
  let component: OperadorOptionComponent;
  let fixture: ComponentFixture<OperadorOptionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [OperadorOptionComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(OperadorOptionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
