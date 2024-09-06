import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListTableOpeComponent } from './list-table-ope.component';

describe('ListTableOpeComponent', () => {
  let component: ListTableOpeComponent;
  let fixture: ComponentFixture<ListTableOpeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ListTableOpeComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ListTableOpeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
