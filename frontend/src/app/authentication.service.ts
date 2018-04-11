import { Injectable } from '@angular/core';

import "rxjs/add/operator/do";
import { Headers, Http, RequestOptions, Response } from "@angular/http";
import { User } from "./model/user";
import "rxjs/add/operator/map";
import { CookieService } from "./cookie.service";
import { Router } from "@angular/router";
import { Observable } from "rxjs/Observable";
import { JwtService } from "./jwt.service";
import { Subject } from "rxjs/Subject";
import { Observer } from 'rxjs/Observer';

const PROTOCOL = "http";
const PORT = 8080;

@Injectable()
export class AuthenticationService {
  user: User;
  userObservable: Observable<User>;
  userObserver: Observer<User>;
  baseUrl: string;

  constructor(private http: Http,
    private cookieService: CookieService,
    private router: Router,
    private jwtService: JwtService) {
    this.userObservable = Observable.create((observer: Observer<User>) => {
      this.userObserver = observer;
    })
    this.baseUrl = `${PROTOCOL}://${location.hostname}:${PORT}`;
    if (this.cookieService.getCookie("access_token") != null &&
      this.cookieService.getCookie("access_token") != undefined) {
      this.updateCurrentUser();
    }
  }

  private updateCurrentUser(): void {
    this.getResource(`/api/users/${this.jwtService.getClaim('id')}`).subscribe(user => {
      this.user = user;
      console.log("user");
      this.setNewUser(user);
    });
  }

  public setNewUser(user: User) {
    this.user = user;
    this.userObserver.next(user);
  }

  authenticate(user: User): Observable<User> {
    return this.http.post(this.baseUrl + '/auth/login', {
      'email': user.email,
      'password': user.password,
      'secret': '3go3i4gho934bO(PF2y83fhfasflknvf8wyf3fasdf@_'
    }).map(response => response.json());
  }

  saveToken(token) {
    this.cookieService.setCookie("access_token", token.jwtToken, 30);
    this.updateCurrentUser();
  }

  getAuthenticatedUser() {
    return this.userObservable;
  }

  getResource(resourceUrl): Observable<any> {
    return this.http.get(this.baseUrl + resourceUrl, this.getOptions())
      .map((res: Response) => res.json());
  }

  public getOptions(): RequestOptions {

    let headers = new Headers({
      'Content-type': "application/json; charset=utf-8",
      'Authorization': 'Bearer ' + this.cookieService.getCookie('access_token')
    });
    return new RequestOptions({ headers: headers });
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
