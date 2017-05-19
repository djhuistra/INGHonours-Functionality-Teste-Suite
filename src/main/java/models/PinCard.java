package models;

import java.util.Date;

public class PinCard {
    private BankAccount bankAccount;
    private int pinCardNumber;
    private int pinCode;
    private Date expirationDate;

    // Temporary method. While expirationDate is not part of the protocol.
    public PinCard(BankAccount bankAccount, int pinCardNumber, int pinCode) {
        this.bankAccount = bankAccount;
        this.pinCardNumber = pinCardNumber;
        this.pinCode = pinCode;
    }

    public PinCard(BankAccount bankAccount, int pinCardNumber, int pinCode, Date expirationDate) {
        this.bankAccount = bankAccount;
        this.pinCardNumber = pinCardNumber;
        this.pinCode = pinCode;
        this.expirationDate = expirationDate;
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    public int getPinCardNumber() {
        return pinCardNumber;
    }

    public void setPinCardNumber(int pinCardNumber) {
        this.pinCardNumber = pinCardNumber;
    }

    public int getPinCode() {
        return pinCode;
    }

    public void setPinCode(int pinCode) {
        this.pinCode = pinCode;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }
}
