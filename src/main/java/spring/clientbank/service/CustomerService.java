package spring.clientbank.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring.clientbank.dao.AccountDAO;
import spring.clientbank.dao.CustomerDAO;
import spring.clientbank.model.Account;
import spring.clientbank.model.Currency;
import spring.clientbank.model.Customer;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerDAO customerDAO;
    private final AccountDAO accountDAO;

    public Customer createCustomer(Customer customer){
        return customerDAO.save(customer);
    }

    public Optional<Customer> getCustomer(Long id){
        return Optional.ofNullable(customerDAO.getOne(id));
    }

    public boolean deleteCustomer(Long id){
        return customerDAO.deleteById(id);
    }

    public List<Customer> getAllCustomers(){
        return customerDAO.getAllCustomers();
    }

    public Optional<Customer> updateCustomer(Customer updatedCustomer){
        return Optional.ofNullable(customerDAO.updateCustomer(updatedCustomer));
    }
    public Optional<Customer> createAccountForCustomer(Long id) {
        Customer customer = customerDAO.getOne(id);
        if(customer != null){
            Account account = new Account(Currency.UAH, customer);
            Account createdAccount = accountDAO.save(account);
            customer.getAccounts().add(createdAccount);
        }
        return updateCustomer(customer);
    }

    public boolean deleteAccountFromCustomer(Long customerId, Long accountId){
        Customer customer = customerDAO.getOne(customerId);
        if(customer != null){
            Account accountToDelete = accountDAO.getOne(accountId);
            if(accountToDelete != null && customer.getAccounts().contains(accountToDelete)){
                accountDAO.delete(accountToDelete);
                customer.getAccounts().remove(accountToDelete);
                updateCustomer(customer);
                return true;
            }
        }
        return false;
    }

}
