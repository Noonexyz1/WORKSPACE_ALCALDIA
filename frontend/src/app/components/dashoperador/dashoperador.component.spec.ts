import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DashoperadorComponent } from './dashoperador.component';

describe('DashoperadorComponent', () => {
  let component: DashoperadorComponent;
  let fixture: ComponentFixture<DashoperadorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DashoperadorComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DashoperadorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
