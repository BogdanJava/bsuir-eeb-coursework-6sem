import { Component } from '@angular/core';
import { User } from "../model/user.model";
import { NgForm } from "@angular/forms";
import { Router, ActivatedRoute } from "@angular/router";
import { AuthenticationService } from "../authentication.service";

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
    private authService: AuthenticationService,
    private route: ActivatedRoute) {
    this.route.params.subscribe(params => {
      if (!params.error) {
        if (authService.isAuthenticated()) {
          router.navigate(['/home']);
        }
      } else {
        this.errorMessage = params.error;
      }
    })
  }

  login(form: NgForm) {
    this.submitted = true;
    if (form.valid) {
      let user = new User();
      user.email = this.email;
      user.password = this.password;
      this.authService.authenticate(user).subscribe(
        data => {
          this.authService.saveToken(data);
          this.router.navigate(['/home']);
        },
        err => {
          console.log(err);
          this.errorMessage = 'Failed to Authenticate';
        });
    } else {
      this.errorMessage = 'Form Data Invalid';
    }
  }

}
