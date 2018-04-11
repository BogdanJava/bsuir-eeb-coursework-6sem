import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from "../authentication.service";
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  constructor(private authService: AuthenticationService,
    private route: ActivatedRoute,
    private router: Router) { }

  ngOnInit() {
    this.route.params.subscribe(params => {
      if (params.user) {
        this.authService.authenticate(params.user).subscribe(
          data => {
            this.authService.saveToken(data);
          },
          error => {
            console.log(error);
            this.router.navigate(['/login', error]);
          });
      }
    });
    this.authService.checkCredentials();
  }

  logout() {
    this.authService.logout();
  }

}
