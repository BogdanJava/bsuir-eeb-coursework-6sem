import { Component, OnInit } from '@angular/core';
import {User} from "../model/user";
import {NgForm} from "@angular/forms";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  moduleId: module.id
})
export class LoginComponent implements OnInit {

  user: User = new User();
  rememberMe: boolean = false;
  submitted: boolean = false;

  /**
   * TODO доработать логику аутентификации, создать сервисы
   * @param {NgForm} form
   */
  submit(form: NgForm): void {
    this.submitted = true;
    if(form.valid) {

    }
  }

  constructor() { }

  ngOnInit() {
  }

}
