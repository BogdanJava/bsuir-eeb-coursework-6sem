import { Component, OnInit } from '@angular/core';
import { User } from '../model/user.model';
import { IMyDpOptions } from 'mydatepicker';
import { AuthenticationService } from '../authentication.service';
import { Router } from '@angular/router';
import { NgForm } from '@angular/forms';
import { UserService } from '../user.service';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {

  user: User = new User();
  submitted: boolean = false;
  errors: string[];
  rePassword: string;
  passwordNoMatch: boolean = false;
  passwordNoMatchMessage: string = "Passwords don't match";
  passwordStrength: string;

  public myDatePickerOptions: IMyDpOptions = {
    dateFormat: 'yyyy-mm-dd',
  };

  constructor(private router: Router,
    private authService: AuthenticationService,
    private userService: UserService) {
    if (authService.isAuthenticated()) {
      router.navigate(['/home']);
    }
  }

  ngOnInit() {
  }

  onSubmit(form: NgForm) {
    this.submitted = true;
    if (form.valid) {
      if (this.user.password !== this.rePassword) {
        this.passwordNoMatch = true;
        console.log("not equals");
        return;
      } else {
        this.passwordNoMatch = false;
        console.log("equals");
      }
      this.user.birthday = this.user.birthday.formatted;
      this.user.gender = this.user.gender.toUpperCase();
      console.log(this.user);
      this.userService.signup(this.user).subscribe(result => {
        if (result.errors) {
          this.errors = result.errors;
        } else {
          this.router.navigate(['/home', this.user]);
        }
      });
    }
  }

  checkPasswordStrength(password: string) {
    if (password.length < 5) {
      this.passwordStrength = "too short";
    } else
      if (password.length >= 5 && password.length < 8) {
        this.passwordStrength = "normal";
      } else {
        this.passwordStrength = "strong";
      }
    return this.passwordStrength;

  }

}
