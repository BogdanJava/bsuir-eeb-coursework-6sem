import { Injectable } from '@angular/core';

import "rxjs/add/operator/do";
import { Headers, Http, RequestOptions, Response } from "@angular/http";
import { User } from "./model/user.model";
import "rxjs/add/operator/map";
import { CookieService } from "./cookie.service";
import { Router } from "@angular/router";
import { Observable } from "rxjs/Observable";
import { JwtService } from "./jwt.service";
import { Subject } from "rxjs/Subject";
import { Observer } from 'rxjs/Observer';
import { JwtHelperService } from '@auth0/angular-jwt';
import { JwtHelper } from 'angular2-jwt';

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
    private jwtService: JwtService,
    private jwtHelper: JwtHelper) {
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

  setNewUser(user: User) {
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

  getOptions(): RequestOptions {

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
    let token = this.cookieService.getCookie('access_token');
    if (token != null) {
      if(this.jwtHelper.isTokenExpired(token)) return false;
      return true;
    } else {return false;}
  }

  logout() {
    console.log('logout');
    this.cookieService.deleteCookie('access_token');
    this.router.navigate(['/login']);
  }


}
