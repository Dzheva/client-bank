package spring.clientbank.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import spring.clientbank.model.Account;
import spring.clientbank.model.Currency;
import spring.clientbank.model.Customer;
import spring.clientbank.repository.AccountRepository;
import spring.clientbank.repository.CustomerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;

    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public Optional<Customer> getCustomer(Long id) {
        return customerRepository.findById(id);
    }

    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer updateCustomer(Customer updatedCustomer) {
        return customerRepository.save(updatedCustomer);
    }

    public Optional<Customer> createAccountForCustomer(Long id) {
        Optional<Customer> optionalCustomer = getCustomer(id);

        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            Account account = new Account(Currency.UAH, customer);
            customer.getAccounts().add(account);
            return Optional.of(updateCustomer(customer));
        }
        return Optional.empty();
    }

    public boolean deleteAccountFromCustomer(Long customerId, Long accountId) {
        Optional<Customer> optionalCustomer = getCustomer(customerId);
        Optional<Account> optionalAccount = accountRepository.findById(accountId);

        if (optionalCustomer.isPresent() && optionalAccount.isPresent()) {
            Customer customer = optionalCustomer.get();
            Account accountToDelete = optionalAccount.get();
            accountRepository.delete(accountToDelete);
            updateCustomer(customer);
            return true;
        }
        return false;
    }

    public Page<Customer> pageGetAllCustomers(Pageable pageable ) {
        return customerRepository.findAll(pageable);
    }

}
