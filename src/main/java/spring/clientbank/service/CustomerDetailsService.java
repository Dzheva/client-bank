package spring.clientbank.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import spring.clientbank.model.Customer;
import spring.clientbank.repository.CustomerRepository;

@Service
@RequiredArgsConstructor
public class CustomerDetailsService implements UserDetailsService {
    private final CustomerRepository customerRepository;

    private UserDetails remapper(Customer customer) {
        return User
                .withUsername(customer.getName())
                .password(customer.getPassword())
                .roles(String.valueOf(customer.getRole()))
                .build();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return customerRepository.findCustomerByEmail(email)
                .map(this::remapper)
                .orElseThrow(() -> new UsernameNotFoundException(
                        String.format("user %s not found", email)
                ));
    }
}
