package com.luizgcl.digitalbank.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AccountType {

    SAVING("Poupança", 26),
    CHECKING("Corrente", 45),;

    String name;
    int code;
}
