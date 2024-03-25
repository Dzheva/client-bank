package spring.clientbank.dto.login;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginRs {
    private Boolean status;
    private String error;
    private String token;

    public static LoginRs Ok(String token) {
        return new LoginRs(true, null, token);
    }

    public static LoginRs Error(String message) {
        return new LoginRs(false, message, null);
    }
}
