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
import { JwtModule, JwtHelperService } from "@auth0/angular-jwt";
import { JwtHelper } from "angular2-jwt";
import { ButtonModule, MenuModule } from "primeng/primeng";
import { UserProfileComponent } from './user-profile/user-profile.component';
import { UserService } from './user.service';
import { RegistrationComponent } from './registration/registration.component';
import { MyDatePickerModule } from 'mydatepicker';
import { UserRoomComponent } from './user-room/user-room.component';
import { ContactInfoComponent } from './contact-info/contact-info.component';
import { PhoneService } from './phone.service';
import { WorkflowComponent } from './workflow/workflow.component';
import { WorkflowDefaultComponent } from './workflow/workflow-default/workflow-default.component';
import { WorkflowAccountsComponent } from './workflow/workflow-accounts/workflow-accounts.component';
import { WorkflowCardsComponent } from './workflow/workflow-cards/workflow-cards.component';
import { CardsService } from './workflow/cards.service';
import { CardRegistrationComponent } from './workflow/workflow-cards/card-registration/card-registration.component';
import { CardDetailsComponent } from './workflow/workflow-cards/card-details/card-details.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    HomeComponent,
    UserProfileComponent,
    RegistrationComponent,
    UserRoomComponent,
    ContactInfoComponent,
    WorkflowComponent,
    WorkflowDefaultComponent,
    WorkflowAccountsComponent,
    WorkflowCardsComponent,
    CardRegistrationComponent,
    CardDetailsComponent
  ],
  imports: [
    BrowserModule, FormsModule, HttpModule, MenuModule, JwtModule, ButtonModule, MyDatePickerModule,
    RouterModule.forRoot([
      { path: "login", component: LoginComponent },
      { path: "signup", component: RegistrationComponent },
      { path: "home", component: HomeComponent, canActivate: [AuthGuard] },
      {
        path: "profile", component: UserRoomComponent, canActivate: [AuthGuard], children: [
          { path: "details", component: UserProfileComponent },
          { path: "contact", component: ContactInfoComponent },
          { path: "**", redirectTo: "/profile/details" }
        ]
      },
      {
        path: "workflow", component: WorkflowComponent, canActivate: [AuthGuard], children: [
          { path: "", component: WorkflowDefaultComponent },
          { path: "cards", component: WorkflowCardsComponent },
          { path: "accounts", component: WorkflowAccountsComponent },
          { path: "cards/registration", component: CardRegistrationComponent },
          { path: "cards/:cardId", component: CardDetailsComponent }
        ]
      },
      { path: "**", redirectTo: "/login" }
    ])
  ],
  providers:
    [
      AuthenticationService,
      CookieService,
      AuthGuard,
      JwtService,
      UserService,
      PhoneService,
      CardsService,
      JwtHelperService,
      JwtHelper
    ],
  bootstrap: [AppComponent]
})
export class AppModule {

}
