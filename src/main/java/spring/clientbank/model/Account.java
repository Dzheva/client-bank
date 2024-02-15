package spring.clientbank.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import java.util.UUID;

@Data
public class Account {
    private Long id;
    private String number;
    private Currency currency;
    private Double balance = 0.0;
    @JsonIgnore
    private Customer customer;

    public Account(Currency currency, Customer customer) {
        this.currency = currency;
        this.customer = customer;
        generateAccountNumber();
    }

    private void generateAccountNumber() {
        this.number = UUID.randomUUID().toString();
    }

}
