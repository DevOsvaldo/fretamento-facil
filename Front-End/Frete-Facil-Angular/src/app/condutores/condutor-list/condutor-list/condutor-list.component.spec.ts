import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CondutorListComponent } from './condutor-list.component';

describe('CondutorListComponent', () => {
  let component: CondutorListComponent;
  let fixture: ComponentFixture<CondutorListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CondutorListComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CondutorListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
