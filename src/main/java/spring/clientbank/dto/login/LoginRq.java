package spring.clientbank.dto.login;

import lombok.Data;

@Data
public class LoginRq {
    private String username;
    private String password;
    private Boolean rememberMe;
}
