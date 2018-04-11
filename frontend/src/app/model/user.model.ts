import { Passport } from "./passport.model";

export class User {
  public email: string;
  public password: string;
  public id: number;
  public firstName: string;
  public lastName: string;
  public birthday: any;
  public passport: Passport = new Passport();
  public gender: string;
}
