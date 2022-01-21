package com.luizgcl.digitalbank.domain;

import com.luizgcl.digitalbank.domain.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@AllArgsConstructor
@Getter
public class Transaction {

    Account accountSender, accountRecipient;
    TransactionType type;
    Double value;
    Date date;
}
