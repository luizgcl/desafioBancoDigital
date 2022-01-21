package com.luizgcl.digitalbank;

import com.luizgcl.digitalbank.domain.Account;
import com.luizgcl.digitalbank.domain.Bank;
import com.luizgcl.digitalbank.domain.Client;
import com.luizgcl.digitalbank.domain.enums.AccountType;
import com.luizgcl.digitalbank.domain.enums.PersonType;
import com.luizgcl.digitalbank.domain.exceptions.AccountWithoutHolderException;

public class Main {

    public static Integer COUNTER = 1;
    public static final Bank BANK = new Bank(1, "Banco Digital");

    public static void main(String[] args) throws AccountWithoutHolderException {
        Client luizSouza = new Client("Luiz Souza", PersonType.PF);

        System.out.println("Criando conta de Pessoa " + luizSouza.getType().getName() + ": " + luizSouza.getName() + "\n");
        Account accountOfLuiz = BANK.createAccount(new Account(luizSouza, AccountType.SAVING));

        Client ronaldoVieira = new Client("Ronaldo Vieira", PersonType.PJ);

        System.out.println("Criando conta de Pessoa "+ ronaldoVieira.getType().getName() + ": " + ronaldoVieira.getName() + "\n");
        Account accountOfRonaldo = BANK.createAccount(new Account(ronaldoVieira, AccountType.SAVING));
        Account accountOfCompany = BANK.createAccount(new Account(ronaldoVieira, "WebSoftware Ltda.", AccountType.CHECKING));
        System.out.print("\n");

        /*
        * Testando todas as operações bancárias criadas.
        * */
        accountOfLuiz.deposit(1500);
        accountOfCompany.setSalt(60000);

        accountOfCompany.transfer(2500, accountOfLuiz);
        accountOfLuiz.withdraw(1000);

        /*
        * Exibindo extrato de todas as contas que realizaram transferências
        * */
        accountOfLuiz.printExtract();
        System.out.println(" ");
        accountOfCompany.printExtract();

        /*
        * Mostrando clientes da instituição bancária
        * */
        mostClients();

        /*
        * Mostrando hipótese de fechamento de conta.
        * */
        BANK.closeAccount(accountOfRonaldo);

        /*
        * Mostrando dados de um cliente específico.
        * */
        mostClient(ronaldoVieira);
    }

    public static void mostClients() {
        System.out.println("\n" + BANK.getName() + ": Clientes Cadastrados:");
        Client lastClient = null;
        for (Account acc : BANK.getAccounts()) {
            if (acc.getHolder() == lastClient)
                continue;
            System.out.println(acc.getHolder().getId() + " " +
                    acc.getHolder().getName() +
                    "\n Contas cadastradas: " + acc.getHolder().getAccounts().size() + "\n");
            lastClient = acc.getHolder();
        }
    }

    public static void mostClient(Client client) {
        System.out.println("=====[{ Informações do Cliente }]=====");
        System.out.println("Cliente: " + client.getName());
        System.out.println(" ");
        System.out.println("Nº de Contas: " + client.getAccounts().size());
        client.getAccounts().forEach((acc, priority) -> {
            System.out.println(acc.getAccountCode() + " - " + priority);
        });
        System.out.println(" ");
        System.out.println("=====[{ " + Main.BANK.getName() + " }]=====");
    }

}
