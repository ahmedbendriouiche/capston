package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class CustomerBalanceResponse{

    private User customer;
    private BigDecimal balance ;

    public CustomerBalanceResponse(){}
    public CustomerBalanceResponse(User customer, BigDecimal balance) {
        this.customer = customer;
        this.balance = balance;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    public User getCustomer() {
        return customer;
    }

    public BigDecimal getBalance() {
        return balance;
    }
}
