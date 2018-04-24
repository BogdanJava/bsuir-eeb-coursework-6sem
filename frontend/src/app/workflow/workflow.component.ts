import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-workflow',
  templateUrl: './workflow.component.html',
  styleUrls: ['./workflow.component.css']
})
export class WorkflowComponent implements OnInit {

  isOpen: boolean = false;
  wrapperDiv = null;
  arrow = null;

  constructor() { }

  ngOnInit() {
  }

  openSideBar() {
    if (!this.wrapperDiv) {
      this.wrapperDiv = document.getElementById('wrapper');
      this.arrow = document.getElementById('arrow');
    }
    this.isOpen = !this.isOpen;
    if (this.isOpen) {
      this.wrapperDiv.className = "toggled";
      this.arrow.className = "glyphicon glyphicon-chevron-left";
    } else {
      this.wrapperDiv.className = "";
      this.arrow.className = "glyphicon glyphicon-chevron-right";
    }
  }

}
