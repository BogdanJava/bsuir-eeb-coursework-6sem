export class PasswordChangeData {
    id: number;
    oldPassword: string;
    newPassword: string;

    constructor(id, oldPassword, newPassword) {
        this.id = id;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }
}