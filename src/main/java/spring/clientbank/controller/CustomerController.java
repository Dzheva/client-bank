package spring.clientbank.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.clientbank.model.Account;
import spring.clientbank.model.Customer;
import spring.clientbank.service.CustomerService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Customer createCustomer(@RequestBody Customer customer){
       customer.setId(null);
       return customerService.createCustomer(customer);
    }

    @GetMapping("{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable ("id") Long id){
        return customerService.getCustomer(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NO_CONTENT).build());
    }

    @GetMapping("all")
    public List<Customer> getAll(){
        return customerService.getAllCustomers();
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public boolean deleteCustomer(@PathVariable("id") Long id){
        return customerService.deleteCustomer(id);
    }


    @PutMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<Customer> updateCustomer(@RequestBody Customer updatedCustomer){
        return customerService.updateCustomer(updatedCustomer)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NO_CONTENT).build());
    }

    @PostMapping("{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Customer> createAccountForCustomer(@PathVariable("id") Long id) {
       return customerService.createAccountForCustomer(id)
               .map(ResponseEntity::ok)
               .orElse(ResponseEntity.status(HttpStatus.NO_CONTENT).build());
    }

    @DeleteMapping("deleteAccount")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public boolean deleteAccountForCustomer(@RequestParam Long customerId, @RequestParam Long accountId) {
       return customerService.deleteAccountFromCustomer(customerId, accountId);
    }

}