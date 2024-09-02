import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RowTableUserComponent } from './row-table-user.component';

describe('RowTableUserComponent', () => {
  let component: RowTableUserComponent;
  let fixture: ComponentFixture<RowTableUserComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RowTableUserComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RowTableUserComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
