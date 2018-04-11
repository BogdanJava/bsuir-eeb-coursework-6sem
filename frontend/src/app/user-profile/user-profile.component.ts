import { Component, OnInit, Input } from '@angular/core';
import { AuthenticationService } from '../authentication.service';
import { User } from '../model/user';
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
    console.log(this.user);
    this.userService.updateUser(this.user)
      .subscribe(user => {
        this.authService.setNewUser(user);
      })
  }

  updatePassword() {
    this.userService.checkPasswordIsCorrect(this.oldPassword, this.user.id)
      .subscribe(correct => {
        if (correct) {
          // todo: implement logic
        }
      })
  }

}
