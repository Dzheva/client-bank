package spring.clientbank.controller.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.clientbank.dto.login.LoginRq;
import spring.clientbank.dto.login.LoginRs;
import spring.clientbank.security.JwtTokenService;
import spring.clientbank.service.CustomerService;

@RestController
@RequiredArgsConstructor
@RequestMapping("auth/api")
public class RestAuthController {
    private final CustomerService customerService;
    private final PasswordEncoder encoder;
    private final JwtTokenService tokenService;

    @PostMapping("login")
    public ResponseEntity<LoginRs> handleLogin(@RequestBody LoginRq rq) {
        return customerService.getCustomerByEmail(rq.getUsername())
                .filter(u -> encoder.matches(rq.getPassword(), u.getPassword()))
                .map(u -> tokenService.generateToken(Math.toIntExact(u.getId()), rq.getRememberMe()))
                .map(LoginRs::Ok)
                .map(ResponseEntity::ok)
                .orElse(
                        ResponseEntity
                                .status(HttpStatus.FORBIDDEN)
                                .body(LoginRs.Error("wrong user/password combination"))
                );
    }
}
