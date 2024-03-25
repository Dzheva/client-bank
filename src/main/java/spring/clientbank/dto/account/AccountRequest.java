package spring.clientbank.dto.account;

import javax.validation.constraints.NegativeOrZero;
import javax.validation.constraints.NotBlank;
import lombok.Value;

@Value
public class AccountRequest {
    @NotBlank(message = "Number cannot be empty")
    private String number;
    @NegativeOrZero(message = "Balance can be negative or zero")
    private Double balance;
}


