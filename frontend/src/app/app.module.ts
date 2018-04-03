import {BrowserModule} from '@angular/platform-browser';
import {InjectionToken, NgModule} from '@angular/core';


import {AppComponent} from './app.component';
import {RouterModule} from "@angular/router";
import {LoginComponent} from './login/login.component';
import {FormsModule} from "@angular/forms";
import {AuthenticationService} from "./authentication.service";
import {HttpModule} from "@angular/http";
import {HomeComponent} from './home/home.component';
import {CookieService} from "./cookie.service";
import {AuthGuard} from "./auth.guard";
import {JwtService} from "./jwt.service";
import {JwtHelperService, JwtModule} from "@auth0/angular-jwt";
import {MenuModule} from "primeng/primeng";


@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    HomeComponent
  ],
  imports: [
    BrowserModule, FormsModule, HttpModule, MenuModule, JwtModule,
    RouterModule.forRoot([
      {path: "login", component: LoginComponent},
      {
        path: "", component: HomeComponent, canActivate: [AuthGuard], children: [
          {path: "home", component: HomeComponent}
        ]
      },
      {path: "**", redirectTo: "/login"}
    ])
  ],
  providers:
    [
      AuthenticationService,
      CookieService,
      AuthGuard,
      JwtService
    ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
