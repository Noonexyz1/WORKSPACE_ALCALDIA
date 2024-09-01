import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RowTableComponent } from './row-table.component';

describe('RowTableComponent', () => {
  let component: RowTableComponent;
  let fixture: ComponentFixture<RowTableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RowTableComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RowTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
