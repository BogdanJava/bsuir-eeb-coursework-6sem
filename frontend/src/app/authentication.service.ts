import {Injectable} from '@angular/core';
import {Observable} from "rxjs/Observable";
import "rxjs/add/operator/do";
import {Http} from "@angular/http";

const PROTOCOL = "http";
const PORT = 8080;

@Injectable()
export class AuthenticationService {
  baseUrl: string;
  authToken: string;

  constructor(private http: Http) {
    this.baseUrl = `${PROTOCOL}://${location.hostname}:${PORT}/`;
  }

  //TODO доделать аутентификаци через токен
  authenticate(): Observable<boolean> {
    return null;
  }

}
