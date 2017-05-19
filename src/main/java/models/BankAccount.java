package models;

import java.util.HashSet;
import java.util.Set;

public class BankAccount {

    private String iBAN;
    private float balance;
    private Set<CustomerAccount> accessReceivers;



    public BankAccount(String iBAN) {
        this.iBAN = iBAN;
        this.accessReceivers = new HashSet<CustomerAccount>();
        this.balance = 0;
    }

    public String getiBAN() {
        return iBAN;
    }

    public Set<CustomerAccount> getAccessReceivers() {
        return accessReceivers;
    }

    public void addAccessReceiver(CustomerAccount customer){
        this.accessReceivers.add(customer);
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public void increaseBalance(float balance) {
        this.balance += balance;
    }

    public void lowerBalance(float balance) {
        this.balance -= balance;
    }


}
