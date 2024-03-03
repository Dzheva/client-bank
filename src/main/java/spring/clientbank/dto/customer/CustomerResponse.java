package spring.clientbank.dto.customer;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import spring.clientbank.dto.account.AccountResponse;
import spring.clientbank.dto.employer.EmployerResponse;
import spring.clientbank.dto.transfer.View;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerResponse {
    @JsonView({View.getAllCustomers.class})
    private Long id;
    @JsonView({View.getAllCustomers.class})
    private String name;
    private String phoneNumber;
    private String email;
    private Integer age;
    @JsonView({View.getAllCustomers.class})
    private List<AccountResponse> accountResponseList;
    private List<EmployerResponse> employerResponseList;
}
