import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListTableRespoComponent } from './list-table-respo.component';

describe('ListTableRespoComponent', () => {
  let component: ListTableRespoComponent;
  let fixture: ComponentFixture<ListTableRespoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ListTableRespoComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ListTableRespoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
