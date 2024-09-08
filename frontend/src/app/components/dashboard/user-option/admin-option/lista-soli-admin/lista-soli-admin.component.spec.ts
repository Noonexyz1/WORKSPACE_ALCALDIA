import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListaSoliAdminComponent } from './lista-soli-admin.component';

describe('ListaSoliAdminComponent', () => {
  let component: ListaSoliAdminComponent;
  let fixture: ComponentFixture<ListaSoliAdminComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ListaSoliAdminComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ListaSoliAdminComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
