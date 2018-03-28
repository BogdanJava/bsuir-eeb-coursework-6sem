import {Injectable} from '@angular/core';
import "rxjs/add/operator/do";
import {Headers, Http, RequestOptions, Response, URLSearchParams} from "@angular/http";
import {User} from "./model/user";
import "rxjs/add/operator/map";
import {CookieService} from "./cookie.service";
import {Router} from "@angular/router";
import {Observable} from "rxjs/Observable";

const PROTOCOL = "http";
const PORT = 8081;

@Injectable()
export class AuthenticationService {
  baseUrl: string;

  constructor(private http: Http,
              private cookieService: CookieService,
              private router: Router) {
    this.baseUrl = `${PROTOCOL}://${location.hostname}:${PORT}/`;
  }

  /*authenticate(user: User): Observable<boolean> {
    return this.http.request(new Request({
      method: RequestMethod.Post,
      url: this.baseUrl + "login",
      body: {name: user.email, password: user.password}
    })).map(response => {
      let res = response.json();
      this.authToken = res.success ? res.token : null;
      return res.success;
    });
  }

  get authenticated(): boolean {
    return this.authToken != null;
  }

  clear() {
    this.authToken = null;
  }*/

  authenticate(user: User) {
    let params = new URLSearchParams();
    params.append('username', user.email);
    params.append('password', user.password);
    params.append('grant_type', 'password');
    params.append('client_id', 'trusted');
    let headers = new Headers({
      'Content-type': 'application/x-www-form-urlencoded; charset=utf-8',
      'Authorization': 'Basic ' + btoa('trusted:secret')
    });
    let options = new RequestOptions({headers: headers});
    this.http.post(this.baseUrl + 'oauth/token', params, options)
      .map(
        response => {
          let res = response.json();
          return res.success;
        }).subscribe(
      data => this.saveToken(data),
      err => console.log(err));
  }

  saveToken(token) {
    this.cookieService.setCookie("access_token", token.access_token, 30);
    this.router.navigate(['/']);
  }

  getResource(resourceUrl): Observable<User> {
    let headers = new Headers({
        'Content-type': "application/x-www/form/urlencoded; charset=utf-8",
        'Authorization': 'Bearer ' + this.cookieService.getCookie('access_token')
      }
    );
    let options = new RequestOptions({headers: headers});
    return this.http.get(resourceUrl, options)
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
