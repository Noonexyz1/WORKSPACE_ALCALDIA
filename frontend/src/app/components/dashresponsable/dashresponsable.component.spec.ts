import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DashresponsableComponent } from './dashresponsable.component';

describe('DashresponsableComponent', () => {
  let component: DashresponsableComponent;
  let fixture: ComponentFixture<DashresponsableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DashresponsableComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DashresponsableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
