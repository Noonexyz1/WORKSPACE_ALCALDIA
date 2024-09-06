import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RowTableOpeComponent } from './row-table-ope.component';

describe('RowTableOpeComponent', () => {
  let component: RowTableOpeComponent;
  let fixture: ComponentFixture<RowTableOpeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RowTableOpeComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RowTableOpeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
