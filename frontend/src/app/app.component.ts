import {Component, OnInit} from '@angular/core';
import {AuthenticationService} from "./authentication.service";
import {MenuItem} from "primeng/primeng";
import {User} from "./model/user.model";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  dropdownItems: any[];
  authenticatedUser: User = null;

  constructor(public authService: AuthenticationService) {
    this.authService.getAuthenticatedUser().subscribe(user => {
      this.authenticatedUser = user;
    })
  }

  logout() {
    this.authService.logout();
  }

  ngOnInit(): void {
  }
  
}
