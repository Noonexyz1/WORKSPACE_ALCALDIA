import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ResponsableOptionComponent } from './responsable-option.component';

describe('ResponsableOptionComponent', () => {
  let component: ResponsableOptionComponent;
  let fixture: ComponentFixture<ResponsableOptionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ResponsableOptionComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ResponsableOptionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
