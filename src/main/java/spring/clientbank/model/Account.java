package spring.clientbank.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;
@Data
@NoArgsConstructor
@Entity
public class Account extends AbstractEntity{
    @Column(name = "account_number", unique = true)
    private String number;
    @Enumerated(EnumType.STRING)
    private Currency currency;
    @Column(name = "balance")
    private Double balance = 0.0;
    @ManyToOne
    @JoinColumn(name = "customer_id")
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
