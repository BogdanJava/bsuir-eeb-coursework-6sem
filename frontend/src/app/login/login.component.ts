import {Component} from '@angular/core';
import {User} from "../model/user";
import {NgForm} from "@angular/forms";
import {Router} from "@angular/router";
import {AuthenticationService} from "../authentication.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  moduleId: module.id
})
export class LoginComponent {

  public email: string;
  public password: string;
  public errorMessage: string;
  public submitted: boolean = false;

  constructor(private router: Router,
              private authService: AuthenticationService) {
  }

  login(form: NgForm) {
    this.submitted = true;
    if (form.valid) {
      let user = new User();
      user.email = this.email;
      user.password = this.password;
      this.authService.authenticate(user);
      if (this.authService.isAuthenticated()) {
        this.router.navigate(['/login']);
      } else {
        this.errorMessage = 'Failed to Authenticate';
      }
    } else {
      this.errorMessage = 'Form Data Invalid';
    }
  }

}
