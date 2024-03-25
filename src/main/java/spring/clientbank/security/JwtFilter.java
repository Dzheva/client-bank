package spring.clientbank.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Log4j2
public class JwtFilter extends OncePerRequestFilter {
    private static final String BEARER = "Bearer ";
    private static final String AUTH_HEADER = HttpHeaders.AUTHORIZATION;

    private final JwtTokenService tokenService;

    private Optional<String> extractTokenFromRequest(HttpServletRequest rq) {
        return Optional.ofNullable(rq.getHeader(AUTH_HEADER))
                .filter(h -> h.startsWith(BEARER))
                .map(h -> h.substring(BEARER.length()));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest rq, HttpServletResponse rs, FilterChain filterChain) throws ServletException, IOException {
        try {
            extractTokenFromRequest(rq)
                    .flatMap(tokenService::parseToken)
                    .map(CustomerDetails::new)
                    .map(cd -> new UsernamePasswordAuthenticationToken(cd, null, cd.getAuthorities()))
                    .ifPresentOrElse(at -> {
                        at.setDetails(new WebAuthenticationDetailsSource().buildDetails(rq));
                        SecurityContextHolder.getContext().setAuthentication(at);
                    }, () -> rs.setStatus(403));
            filterChain.doFilter(rq, rs);
        } catch (Exception ex) {
            log.error("something went wrong in filter {}", ex.getMessage());
        }
    }
}
