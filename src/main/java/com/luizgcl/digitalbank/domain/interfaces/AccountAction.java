package com.luizgcl.digitalbank.domain.interfaces;

import com.luizgcl.digitalbank.domain.Account;

public interface AccountAction {

    void transfer(double value, Account recipient);
    void withdraw(double value);
    void deposit(double value);

    void printAccountDetails();
    void printExtract();

    void close();

}
