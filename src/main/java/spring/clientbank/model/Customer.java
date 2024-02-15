package spring.clientbank.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Customer {
    private Long id;
    private String name;
    private String email;
    private Integer age;

    private List<Account> accounts = new ArrayList<>();

    public Customer(String name, String email, Integer age) {
        this.name = name;
        this.email = email;
        this.age = age;
    }
}
