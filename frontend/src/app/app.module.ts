import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';


import {AppComponent} from './app.component';
import {RouterModule} from "@angular/router";
import {LoginComponent} from './login/login.component';
import {FormsModule} from "@angular/forms";
import {AuthenticationService} from "./authentication.service";
import {HttpModule} from "@angular/http";
import {HomeComponent} from './home/home.component';
import {CookieService} from "./cookie.service";


@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    HomeComponent
  ],
  imports: [
    BrowserModule, FormsModule, HttpModule,
    RouterModule.forRoot([
      {path: "login", component: LoginComponent},
      {path: "home", component: HomeComponent},
      {path: "**", redirectTo: "/login"}
    ])
  ],
  providers:
    [
      AuthenticationService,
      CookieService
    ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
