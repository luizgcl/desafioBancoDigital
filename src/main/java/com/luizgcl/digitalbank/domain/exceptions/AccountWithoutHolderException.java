package com.luizgcl.digitalbank.domain.exceptions;

/*
* Excessão criada apenas para mostrar como seria feito,
* existem muitas exceções que podem ser tratadas no projeto
* Provavelmente adicionarei futuramente.
* ~ luizgcl - 21/01/22;
* */
public class AccountWithoutHolderException extends Exception{

    public AccountWithoutHolderException() {
        super("Conta sem titular definido!");
    }

}
