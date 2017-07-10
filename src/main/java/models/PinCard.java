package models;

import java.util.Date;

public class PinCard {
    private BankAccount bankAccount;
    private String pinCardNumber;
    private String pinCode;
    private Date expirationDate;
    private int failedAttempts = 0;
    private Boolean blocked = false;

    // Temporary method. While expirationDate is not part of the protocol.
    public PinCard(BankAccount bankAccount, String pinCardNumber, String pinCode) {
        this.bankAccount = bankAccount;
        this.pinCardNumber = pinCardNumber;
        this.pinCode = pinCode;
    }

    public PinCard(BankAccount bankAccount, String pinCardNumber, String pinCode, Date expirationDate) {
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

    public String getPinCardNumber() {
        return pinCardNumber;
    }

    public void setPinCardNumber(String pinCardNumber) {
        this.pinCardNumber = pinCardNumber;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }


    public int getFailedAttempts() {
        return failedAttempts;
    }

    public void setFailedAttempts(int failedAttempts) {
        this.failedAttempts = failedAttempts;
    }

    public Boolean getBlocked() {
        return blocked;
    }

    public void setBlocked(Boolean blocked) {
        this.blocked = blocked;
    }
}
