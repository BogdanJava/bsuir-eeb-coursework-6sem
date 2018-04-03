import {Component, OnInit} from '@angular/core';
import {AuthenticationService} from "./authentication.service";
import {MenuItem} from "primeng/primeng";
import {User} from "./model/user";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  dropdownItems: MenuItem[];
  authenticatedUser: User = null;

  constructor(public authService: AuthenticationService) {
    this.authService.getAuthenticatedUser().subscribe(user => {
      this.authenticatedUser = user;
    })
  }


  ngOnInit(): void {
    this.dropdownItems = [
      {
        label: 'Log out', icon: 'fa-sign-out', command: () => {
          console.log("logout");
          this.authService.logout()
        }
      }
    ];
  }
}
