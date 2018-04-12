import { Component, OnInit, ChangeDetectorRef, Renderer, ViewChildren } from '@angular/core';
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
  passwordStrengthBarWidth: number = 0;
  progressBar: Element = null;
  progressBarClass: string;

  public myDatePickerOptions: IMyDpOptions = {
    dateFormat: 'yyyy-mm-dd',
  };

  constructor(private router: Router,
    private authService: AuthenticationService,
    private userService: UserService,
    private renderer: Renderer,
    private cdr: ChangeDetectorRef) {
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
        console.log(result);
        if (result.errors) {
          this.errors = result.errors;
          console.log("errors: " + this.errors);
        } else {
          console.log('navigate');
          this.authService.authenticate(this.user).subscribe(data => {
            this.authService.saveToken(data);
            this.router.navigate(['/home', result]);
          })
        }
      });
    }
  }

  setupProgressBar(password: string) {
    if (password.length < 5) {
      this.passwordStrength = "too short";
      this.passwordStrengthBarWidth = 33;
      this.progressBarClass = "progress-bar-danger";
    } else
      if (password.length >= 5 && password.length < 8) {
        this.passwordStrength = "normal";
        this.passwordStrengthBarWidth = 66;
        this.progressBarClass = "progress-bar-warning";
      } else {
        this.passwordStrength = "strong";
        this.passwordStrengthBarWidth = 100;
        this.progressBarClass = "progress-bar-success";
      }
  }

  onPasswordInput() {
    if(this.user.password.length == 0) {
      this.progressBar = null;
      return;
    }
    this.cdr.detectChanges();
    if (this.progressBar == null) {
      this.progressBar = document.getElementById('progressBar');
    }
    this.setupProgressBar(this.user.password);

    console.log(this.progressBarClass);
    this.renderer.setElementStyle(this.progressBar, "width", `${this.passwordStrengthBarWidth}%`);
    this.progressBar.setAttribute("class", "progress-bar " + this.progressBarClass);
    this.cdr.detectChanges();
  }

}
