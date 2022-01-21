package com.luizgcl.digitalbank.domain;

import com.luizgcl.digitalbank.Main;
import com.luizgcl.digitalbank.domain.exceptions.AccountWithoutHolderException;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
public class Bank {

    private Integer agency;
    private String name;

    private List<Account> accounts = new ArrayList<>();

    public Bank(int agency, String name) {
        this.agency = agency;
        this.name = name;
    }

    public Account createAccount(Account account) throws AccountWithoutHolderException {
        if (account.getHolder() == null) throw new AccountWithoutHolderException();
        accounts.add(account);

        System.out.println(Main.BANK.getName() + ": Conta Criada Com sucesso!\n");
        account.printAccountDetails();

        return account;
    }

    public void closeAccount(Account account) {
        accounts.remove(account);
        Client holder = account.getHolder();

        if (holder.getAccounts().size() > 1) {
            for (Map.Entry<Account, Integer> accountIntegerEntry : holder.getAccounts().entrySet()) {
                if (!(accountIntegerEntry.getKey() == account && accountIntegerEntry.getValue() == 0)) {
                    accountIntegerEntry.setValue(0);
                }
            }
        }

        account.close();
        System.out.println(Main.BANK.getName() + ": Conta fechada com sucesso!\n");
    }

    public int getCurrentAccountId() {
        return accounts.size()%100==0 ? accounts.size()/100 : 1;
    }

}
