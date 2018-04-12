import { Component, OnInit, Input } from '@angular/core';
import { AuthenticationService } from '../authentication.service';
import { User } from '../model/user.model';
import { UserService } from '../user.service';
import { CookieService } from '../cookie.service';
import { JwtService } from '../jwt.service';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css']
})
export class UserProfileComponent implements OnInit {

  user: User;
  oldPassword: string;
  newPassword: string;
  repeatedNewPassword: string;
  passwordIncorrect = false;
  passwordChanged = false;
  passwordsNotMatch = false;

  constructor(private authService: AuthenticationService,
    private userService: UserService,
    private jwtService: JwtService) {
  }

  // think about it. Why subject's subscription doesn't work
  /* ngOnInit() {
    this.authService.getAuthenticatedUser()
    .subscribe(user => {
      this.user = user;
    });
  } */

  ngOnInit() {
    let id = this.jwtService.getClaim('id');
    this.userService.getUser(id)
      .subscribe(user => {
        this.user = user;
        this.user.id = id;
      });
  }

  onSubmit() {
    this.userService.updateUser(this.user)
      .subscribe(user => {
        this.authService.setNewUser(user);
      })
  }

  updatePassword() {
    this.passwordsNotMatch = this.repeatedNewPassword != this.newPassword;
    if(this.passwordsNotMatch) return;
    this.userService.checkPasswordIsCorrect(this.oldPassword, this.user.id)
      .subscribe(correct => {
        if (correct) {
          this.passwordIncorrect = null;
          this.userService.changePassword(this.user.id, this.oldPassword, this.newPassword).subscribe(result => {
            console.log(result);
            this.passwordChanged = result.success;
            console.log(this.passwordChanged);
          });
        } else {
          this.passwordIncorrect = true;
        }
      });
  }

}
