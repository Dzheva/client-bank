package spring.clientbank.dao;

import org.springframework.stereotype.Repository;
import spring.clientbank.model.Account;
import spring.clientbank.model.Customer;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CustomerDAO implements DAO<Customer>{

    private final List<Customer> customers = new ArrayList<>();
    private long nextId = 1;
    @Override
    public Customer save(Customer customer) {
        if(customer.getId() == null){
            customer.setId(nextId++);
        }
        customers.add(customer);
        return customer;
    }

    @Override
    public boolean delete(Customer customer) {
        return customers.remove(customer);
    }

    @Override
    public void deleteAll(List<Customer> customersList) {
        customers.removeAll(customersList);
    }

    @Override
    public void saveAll(List<Customer> customersList) {
        customersList.forEach(customer -> save(customer));
    }

    @Override
    public boolean deleteById(Long id) {
        return delete(getOne(id));
    }

    @Override
    public Customer getOne(Long id) {
        return customers.
                stream().
                filter(customer -> customer.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public List<Customer> getAllCustomers(){
        return customers;
    }

    public Customer updateCustomer(Customer updatedCustomer){
        Customer existingCustomer = getOne(updatedCustomer.getId());

        if(existingCustomer != null){
            existingCustomer.setName(updatedCustomer.getName());
            existingCustomer.setEmail(updatedCustomer.getEmail());
            existingCustomer.setAge(updatedCustomer.getAge());
            existingCustomer.setAccounts(updatedCustomer.getAccounts());
        }

        return existingCustomer;
    }

}
