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

  login(form: NgForm): void {
    this.submitted = true;
    console.log("kek1");
    if (form.valid) {
      console.log("kek1");
      this.authService.authenticate(new User(this.email, this.password));
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
