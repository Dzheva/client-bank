package spring.clientbank.dto.customer;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import spring.clientbank.dto.account.AccountResponse;
import spring.clientbank.dto.employer.EmployerResponse;
import spring.clientbank.model.Customer;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class CustomerMapper {
    private final ModelMapper modelMapper;

    public CustomerMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Customer customerRequestToCustomer(CustomerRequest customerRequest) {
        return modelMapper.map(customerRequest, Customer.class);
    }

    public CustomerResponse customerToCustomerResponse(Customer customer) {
        CustomerResponse customerResponse = modelMapper.map(customer, CustomerResponse.class);

        List<AccountResponse> accountResponseList = Optional.ofNullable(customer.getAccounts())
                .orElse(Collections.emptyList())
                .stream()
                .map(account -> modelMapper.map(account, AccountResponse.class))
                .collect(Collectors.toList());
        customerResponse.setAccountResponseList(accountResponseList);

        List<EmployerResponse> employerResponseList = Optional.ofNullable(customer.getEmployers())
                .orElse(Collections.emptyList())
                .stream()
                .map(employer -> modelMapper.map(employer, EmployerResponse.class))
                .collect(Collectors.toList());
        customerResponse.setEmployerResponseList(employerResponseList);

        return customerResponse;
    }
}
