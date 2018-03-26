export class User {

  constructor(public email?: string,
              public password?: string,
              public id?: number) {
    this.email = email;
    this.password = password;
    this.id = id;
  }
}
