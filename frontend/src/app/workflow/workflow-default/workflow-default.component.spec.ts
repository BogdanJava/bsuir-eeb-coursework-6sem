import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { WorkflowDefaultComponent } from './workflow-default.component';

describe('WorkflowDefaultComponent', () => {
  let component: WorkflowDefaultComponent;
  let fixture: ComponentFixture<WorkflowDefaultComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ WorkflowDefaultComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(WorkflowDefaultComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
