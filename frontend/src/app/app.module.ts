import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';


import { AppComponent } from './app.component';
import { RouterModule } from "@angular/router";
import { LoginComponent } from './login/login.component';
import { FormsModule } from "@angular/forms";
import { AuthenticationService } from "./authentication.service";
import { HttpModule } from "@angular/http";
import { HomeComponent } from './home/home.component';
import { CookieService } from "./cookie.service";
import { AuthGuard } from "./auth.guard";
import { JwtService } from "./jwt.service";
import { JwtModule } from "@auth0/angular-jwt";
import { ButtonModule, MenuModule } from "primeng/primeng";
import { UserProfileComponent } from './user-profile/user-profile.component';
import { UserService } from './user.service';
import { RegistrationComponent } from './registration/registration.component';
import { MyDatePickerModule } from 'mydatepicker';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    HomeComponent,
    UserProfileComponent,
    RegistrationComponent
  ],
  imports: [
    BrowserModule, FormsModule, HttpModule, MenuModule, JwtModule, ButtonModule, MyDatePickerModule,
    RouterModule.forRoot([
      { path: "login", component: LoginComponent },
      { path: "signup", component: RegistrationComponent },
      { path: "home", component: HomeComponent, canActivate: [AuthGuard] },
      { path: "profile", component: UserProfileComponent, canActivate: [AuthGuard] },
      { path: "**", redirectTo: "/login" }
    ])
  ],
  providers:
    [
      AuthenticationService,
      CookieService,
      AuthGuard,
      JwtService,
      UserService
    ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
