import { Phone } from "../phone.model";

export class PhoneDTO {
    public phone: Phone;
    public userId: number;

    constructor(phone, userId) {
        this.phone = phone;
        this.userId = userId;
    }
}