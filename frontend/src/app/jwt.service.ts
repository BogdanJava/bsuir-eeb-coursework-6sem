import {Injectable} from '@angular/core';
import {CookieService} from "./cookie.service";
import {JwtHelperService} from "@auth0/angular-jwt";

@Injectable()
export class JwtService {
  private jwtService: JwtHelperService;

  constructor(private cookieService: CookieService) {
    this.jwtService = new JwtHelperService();
  }

  getClaim(name: string): any {
    let token = this.cookieService.getCookie('access_token');
    let decodedToken = this.jwtService.decodeToken(token);
    return decodedToken[name];
  }

}
