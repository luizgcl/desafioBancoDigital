package com.luizgcl.digitalbank.domain;

import com.luizgcl.digitalbank.Main;
import com.luizgcl.digitalbank.domain.enums.PersonType;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class Client implements Comparable<Client> {

    Integer id;
    String name;

    PersonType type;

    Map<Account, Integer> accounts = new HashMap<>();

    public Client(String name, PersonType type) {
        this.id = Main.COUNTER++;

        this.name = name;
        this.type = type;
    }

    @Override
    public int compareTo(Client client) {
        return this.getId().compareTo(client.getId());
    }

}
