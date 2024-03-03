package spring.clientbank.controller;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import spring.clientbank.dto.customer.CustomerMapper;
import spring.clientbank.dto.customer.CustomerRequest;
import spring.clientbank.dto.customer.CustomerResponse;
import spring.clientbank.dto.transfer.View;
import spring.clientbank.model.Customer;
import spring.clientbank.service.CustomerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;
    private final CustomerMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CustomerResponse> createCustomer(@Validated @RequestBody CustomerRequest customerRequest) {
        Customer customer = mapper.customerRequestToCustomer(customerRequest);
        Customer createdCustomer = customerService.createCustomer(customer);
        CustomerResponse customerResponse = mapper.customerToCustomerResponse(createdCustomer);
        return new ResponseEntity<>(customerResponse, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<CustomerResponse> getCustomer(@PathVariable("id") Long id) {
        return customerService.getCustomer(id)
                .map(mapper::customerToCustomerResponse)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NO_CONTENT).build());
    }

    @GetMapping("all")
    @JsonView({View.getAllCustomers.class})
    public List<CustomerResponse> getAll() {
        return customerService.getAllCustomers().stream()
                .map(mapper::customerToCustomerResponse)
                .collect(Collectors.toList());
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteCustomer(@PathVariable("id") Long id) {
        customerService.deleteCustomer(id);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public CustomerResponse updateCustomer(@Validated @RequestBody CustomerRequest customerRequest) {
        Customer customer = mapper.customerRequestToCustomer(customerRequest);
        Customer updatedCustomer = customerService.updateCustomer(customer);
        CustomerResponse customerResponse = mapper.customerToCustomerResponse(updatedCustomer);
        return customerResponse;
    }

    @PostMapping("{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CustomerResponse> createAccountForCustomer(@PathVariable("id") Long id) {
        return customerService.createAccountForCustomer(id)
                .map(mapper::customerToCustomerResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NO_CONTENT).build());
    }

    @DeleteMapping("deleteAccount")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public boolean deleteAccountForCustomer(@RequestParam Long customerId, @RequestParam Long accountId) {
        return customerService.deleteAccountFromCustomer(customerId, accountId);
    }


    @GetMapping("/page-all-customers")
    public Page<CustomerResponse> getAllCustomers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Customer> pageCustomers = customerService.pageGetAllCustomers(pageable);
        Page<CustomerResponse> result = pageCustomers.map(mapper::customerToCustomerResponse);
        return result;
    }
}
