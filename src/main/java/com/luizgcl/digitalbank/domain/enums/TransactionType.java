package com.luizgcl.digitalbank.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TransactionType {

    PAYMENT("Pagamento", 888),
    RECEIPT("Recebimento", 778),
    DEPOSIT("Depósito", 668),
    WITHDRAW("Saque", 558),;

    String description;
    int code;

}
