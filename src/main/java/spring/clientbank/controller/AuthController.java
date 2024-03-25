package spring.clientbank.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import spring.clientbank.dto.customer.CustomerMapper;
import spring.clientbank.dto.customer.CustomerRequest;
import spring.clientbank.model.Customer;
import spring.clientbank.model.Role;
import spring.clientbank.security.JwtTokenService;
import spring.clientbank.service.CustomerService;

@Controller
@RequiredArgsConstructor
@RequestMapping("auth")
public class AuthController {
    private final CustomerService customerService;
    private final CustomerMapper mapper;
    private final PasswordEncoder encoder;

    private final JwtTokenService tokenService;

    @GetMapping("registration")
    public String registrationPage(@ModelAttribute("customerRequest") CustomerRequest customerRequest) {
        return "registration";
    }

    @GetMapping("mylogin")
    public String loginPage() {
        return "mylogin";
    }

    @PostMapping("registration")
    public String performRegistration(@ModelAttribute("customerRequest") CustomerRequest customerRequest) {
        Customer customer = mapper.customerRequestToCustomer(customerRequest);
        customer.setPassword(encoder.encode(customerRequest.getPassword()));
        customer.setRole(Role.USER);
        customerService.createCustomer(customer);
        return "mylogin";
    }

}
