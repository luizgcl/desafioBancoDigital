package com.luizgcl.digitalbank.domain;

import com.luizgcl.digitalbank.Main;
import com.luizgcl.digitalbank.domain.enums.AccountType;
import com.luizgcl.digitalbank.domain.enums.TransactionType;
import com.luizgcl.digitalbank.domain.interfaces.AccountAction;
import lombok.Getter;
import lombok.Setter;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
public class Account implements AccountAction {

    private Client holder;
    private String accountCode;
    private String nickname;

    private Integer bankAgency;
    private Integer accountNumber;
    private Integer accountId;

    private AccountType type;

    @Setter
    private double salt = 0;

    private List<Transaction> transactions = new ArrayList<>();

    public Account(Client client, AccountType type) {
        this(client, client.getName(), type);
    }

    private boolean hasNickname;

    public Account(Client client, String nickname, AccountType type) {
        this.holder = client;
        this.type = type;
        this.nickname = nickname;

        bankAgency = Main.BANK.getAgency();
        accountNumber = Main.BANK.getAccounts().size()+1;
        accountId = Main.BANK.getCurrentAccountId();

        accountCode = bankAgency + " " + type.getCode() + " 000" + accountNumber + "-" + accountId;

        if (holder.getAccounts().size() == 0) {
            holder.getAccounts().put(this, 0);
        } else {
            holder.getAccounts().put(this, holder.getAccounts().size());
        }

        hasNickname = !client.getName().equals(nickname);
    }

    public String getSaltFormatted() {
        return new DecimalFormat("#,###.##").format(salt);
    }

    @Override
    public void transfer(double value, Account recipient) {

        salt -= value;
        recipient.setSalt(recipient.getSalt()+value);

        transactions.add(
                new Transaction(this, recipient,
                        TransactionType.PAYMENT, value,
                        new Date(System.currentTimeMillis())));
        recipient.getTransactions().add(new Transaction(this, recipient,
                TransactionType.RECEIPT, value,
                new Date(System.currentTimeMillis())));
    }

    @Override
    public void withdraw(double value) {
        salt -= value;
        transactions.add(
                new Transaction(this, null,
                        TransactionType.WITHDRAW, value,
                        new Date(System.currentTimeMillis())));
    }

    @Override
    public void deposit(double value) {
        salt += value;
        transactions.add(
                new Transaction(this, null,
                        TransactionType.DEPOSIT, value,
                        new Date(System.currentTimeMillis())));
    }

    @Override
    public void printAccountDetails() {
        System.out.println("=====[{ Informações da Conta }]=====");
        System.out.println("Conta: " + getAccountCode());
        if (hasNickname)
            System.out.println("Apelido: " + getNickname());
        System.out.println(" ");
        System.out.println("Titular: " + holder.getName());
        System.out.println("    Pessoa " + holder.getType().getName());
        System.out.println("Tipo de conta: Conta " + type.getName());
        System.out.println("Saldo: R$" + getSaltFormatted());
        System.out.println(" ");
        System.out.println("=====[{ " + Main.BANK.getName() + " }]=====");
    }

    @Override
    public void printExtract() {
        if (transactions.size() == 0) {
            System.out.println("=====[{ Extrato da Conta }]=====");
            System.out.println("Conta: " + getAccountCode());
            if (hasNickname)
                System.out.println("Apelido: " + getNickname());
            System.out.println(" ");
            System.out.println("Essa conta não possui nenhuma transferência.");
            System.out.println("=====[{ " + Main.BANK.getName() + " }]=====");
            return;
        }
        System.out.println("=====[{ Extrato da Conta }]=====");
        System.out.println("Conta: " + getAccountCode());
        if (hasNickname)
            System.out.println("Apelido: " + getNickname());
        System.out.println(" ");

        List<Transaction> currentTransactions = transactions.stream()
                .sorted((t1, t2) -> t2.getDate().compareTo(t1.getDate()))
                .limit(5).collect(Collectors.toList());

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        for (Transaction t : currentTransactions) {
            StringBuffer buffer = new StringBuffer();
            buffer.append(sdf.format(t.getDate())).append(" - ")
            .append(t.getType().getDescription());

            switch (t.getType()) {
                case DEPOSIT:
                case RECEIPT: {
                    buffer.append(" + ");
                    break;
                }
                case WITHDRAW:
                case PAYMENT: {
                    buffer.append(" - ");
                    break;
                }
            }

            buffer.append("R$").append(new DecimalFormat("#,###.##").format(t.getValue()));

            System.out.println(buffer.toString());
        }

        System.out.println(" ");
        System.out.println("Saldo atual: R$" + getSaltFormatted());
        System.out.println(" ");
        System.out.println("Apenas são mostradas as 5 transferências mais recentes,");
        System.out.println("caso queira solicitar o extrato completo entre em contato conosco.");
        System.out.println("=====[{ " + Main.BANK.getName() + " }]=====");
    }

    @Override
    public void close() {
        holder.getAccounts().remove(this);
    }

}
