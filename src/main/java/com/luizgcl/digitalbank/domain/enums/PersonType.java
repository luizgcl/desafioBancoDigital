package com.luizgcl.digitalbank.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PersonType {

    PJ("Jurídica", 878),
    PF("Física", 121),;

    String name;
    int code;

}
