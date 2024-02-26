package spring.clientbank.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring.clientbank.model.Customer;
import spring.clientbank.model.Employer;
import spring.clientbank.repository.CustomerRepository;
import spring.clientbank.repository.EmployerRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployerService {
    private final EmployerRepository employerRepository;
    private final CustomerRepository customerRepository;

    public Employer createEmployer(Employer employer){
        return employerRepository.save(employer);
    }

    public Optional<Employer> getEmployerById(Long id){
        return employerRepository.findById(id);
    }

    public void deleteEmployerById(Long id){
        employerRepository.deleteById(id);
    }

    public Employer updateEmployer(Employer updatedEmployer){
        return employerRepository.save(updatedEmployer);
    }

    public Optional<Employer> addEmployerToCustomer(Long customerId, Long employerId){
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
        Optional<Employer> optionalEmployer = getEmployerById(employerId);

        if(optionalCustomer.isPresent() && optionalEmployer.isPresent()){
            Customer customer = optionalCustomer.get();
            Employer employer = optionalEmployer.get();
            customer.getEmployers().add(employer);
            employer.getCustomers().add(customer);
            customerRepository.save(customer);
            return Optional.of(updateEmployer(employer));
        }
        return Optional.empty();
    }
}
