import {Injectable} from '@angular/core';

import "rxjs/add/operator/do";
import {Headers, Http, RequestOptions, Response} from "@angular/http";
import {User} from "./model/user";
import "rxjs/add/operator/map";
import {CookieService} from "./cookie.service";
import {Router} from "@angular/router";
import {Observable} from "rxjs/Observable";
import {JwtService} from "./jwt.service";
import {Subject} from "rxjs/Subject";

const PROTOCOL = "http";
const PORT = 8080;

@Injectable()
export class AuthenticationService {
  authenticatedUser: Subject<User> = new Subject<User>();
  baseUrl: string;

  constructor(private http: Http,
              private cookieService: CookieService,
              private router: Router,
              private jwtService: JwtService) {
    this.baseUrl = `${PROTOCOL}://${location.hostname}:${PORT}`;
    if (this.cookieService.getCookie("access_token") != null &&
    this.cookieService.getCookie("access_token") != undefined) {
      this.updateCurrentUser();
    }
  }

  private updateCurrentUser(): void {
    this.getResource(`/api/users/${this.jwtService.getClaim('id')}`).subscribe(user => {
      this.authenticatedUser.next(user);
      this.router.navigate(['/home']);
    });
  }

  authenticate(user: User) {
    this.http.post(this.baseUrl + '/auth/login', {
      'email': user.email,
      'password': user.password,
      'secret': '3go3i4gho934bO(PF2y83fhfasflknvf8wyf3fasdf@_'
    }).map(response => response.json()).subscribe(
      data => this.saveToken(data),
      err => console.log(err));
  }

  saveToken(token) {
    this.cookieService.setCookie("access_token", token.jwtToken, 30);
    this.updateCurrentUser();
  }

  getAuthenticatedUser(): Observable<User> {
    return this.authenticatedUser.asObservable();
  }


  getResource(resourceUrl): Observable<any> {
    let headers = new Headers({
        'Content-type': "application/json; charset=utf-8",
        'Authorization': 'Bearer ' + this.cookieService.getCookie('access_token')
      }
    );
    let options = new RequestOptions({headers: headers});
    return this.http.get(this.baseUrl + resourceUrl, options)
      .map((res: Response) => res.json());
  }

  checkCredentials() {
    if (!this.cookieService.getCookie('access_token')) {
      this.router.navigate(['/login']);
    }
  }

  isAuthenticated(): boolean {
    return this.cookieService.getCookie('access_token') != null;
  }

  logout() {
    this.cookieService.deleteCookie('access_token');
    this.router.navigate(['/login']);
  }


}
