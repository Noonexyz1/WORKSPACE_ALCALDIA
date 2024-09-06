import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListTableSoliComponent } from './list-table-soli.component';

describe('ListTableSoliComponent', () => {
  let component: ListTableSoliComponent;
  let fixture: ComponentFixture<ListTableSoliComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ListTableSoliComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ListTableSoliComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
