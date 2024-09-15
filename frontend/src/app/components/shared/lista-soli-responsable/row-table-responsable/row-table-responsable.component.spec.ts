import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RowTableResponsableComponent } from './row-table-responsable.component';

describe('RowTableResponsableComponent', () => {
  let component: RowTableResponsableComponent;
  let fixture: ComponentFixture<RowTableResponsableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RowTableResponsableComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RowTableResponsableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
