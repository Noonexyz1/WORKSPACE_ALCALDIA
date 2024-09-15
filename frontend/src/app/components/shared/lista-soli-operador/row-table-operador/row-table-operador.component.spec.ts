import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RowTableOperadorComponent } from './row-table-operador.component';

describe('RowTableOperadorComponent', () => {
  let component: RowTableOperadorComponent;
  let fixture: ComponentFixture<RowTableOperadorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RowTableOperadorComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RowTableOperadorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
