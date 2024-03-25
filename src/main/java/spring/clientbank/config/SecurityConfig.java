package spring.clientbank.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import spring.clientbank.model.Customer;
import spring.clientbank.model.Role;
import spring.clientbank.security.JwtFilter;
import spring.clientbank.service.CustomerDetailsService;
import spring.clientbank.service.CustomerService;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Set;

@Configuration
@EnableWebSecurity
@EnableGlobalAuthentication
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private final CustomerDetailsService cds;

    private final CustomerService cs;

    private final JwtFilter jwtFilter;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/auth/**").permitAll()
                .antMatchers("/h2-console/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/auth/mylogin")
                .loginProcessingUrl("/login").defaultSuccessUrl("/hello").permitAll()
                .and()
                .logout().logoutSuccessUrl("/auth/mylogin")
                .invalidateHttpSession(true).deleteCookies("JSESSIONID")
                .and()
                .oauth2Login().defaultSuccessUrl("/hello")
                .userInfoEndpoint(userInfo -> userInfo.oidcUserService(oidcUService()));

        http.headers().frameOptions().sameOrigin();
        http.csrf().disable();
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }

    private OAuth2UserService<OidcUserRequest, OidcUser> oidcUService() {
        return userRequest -> {
            String email = userRequest.getIdToken().getEmail();

            if (cs.getCustomerByEmail(email).isEmpty()) {
                String name = userRequest.getIdToken().getFullName();
                Customer customer = new Customer();
                customer.setEmail(email);
                customer.setName(name);
                customer.setAge(0);
                customer.setPassword("");
                customer.setRole(Role.USER);
                cs.createCustomer(customer);
            }

            UserDetails userDetails = cds.loadUserByUsername(email);

            DefaultOidcUser oidcUser = new DefaultOidcUser(userDetails.getAuthorities(), userRequest.getIdToken());
            Set<Method> userDetailsMethods = Set.of(UserDetails.class.getMethods());

            return (OidcUser) Proxy.newProxyInstance(WebSecurityConfiguration.class.getClassLoader(),
                    new Class[]{UserDetails.class, OidcUser.class}, (proxy, method, args) ->
                            userDetailsMethods.contains(method)
                                    ? method.invoke(userDetails, args) : method.invoke(oidcUser, args));
        };
    }

    @Override
    protected void configure(AuthenticationManagerBuilder amb) throws Exception {
        amb.userDetailsService(cds).passwordEncoder(getPasswordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
