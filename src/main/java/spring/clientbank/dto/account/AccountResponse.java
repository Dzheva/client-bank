package spring.clientbank.dto.account;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import spring.clientbank.dto.transfer.View;
import spring.clientbank.model.Currency;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonView({View.getAllCustomers.class})
public class AccountResponse {
    private Long id;
    private String number;
    private Currency currency;
    private Double balance;
}
