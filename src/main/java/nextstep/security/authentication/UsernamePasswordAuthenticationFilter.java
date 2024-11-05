package nextstep.security.authentication;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nextstep.security.context.HttpSessionSecurityContextRepository;
import nextstep.security.context.SecurityContext;
import nextstep.security.context.SecurityContextHolder;
import nextstep.security.userdetails.UserDetailsService;
import org.springframework.web.filter.GenericFilterBean;

import static nextstep.security.utils.URLConstants.DEFAULT_REQUEST_URI;
import static nextstep.security.utils.URLConstants.MEMBER_ME_REQUEST_URI;

public class UsernamePasswordAuthenticationFilter extends GenericFilterBean {

    private static final List<String> allowedURIList = List.of(
            DEFAULT_REQUEST_URI, MEMBER_ME_REQUEST_URI
    );
    private final AuthenticationManager authenticationManager;
    private final HttpSessionSecurityContextRepository securityContextRepository = new HttpSessionSecurityContextRepository();

    public UsernamePasswordAuthenticationFilter(UserDetailsService userDetailsService) {
        this.authenticationManager = new ProviderManager(
                List.of(new DaoAuthenticationProvider(userDetailsService))
        );
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        if (!allowedURIList.contains(((HttpServletRequest) request).getRequestURI())) {
            chain.doFilter(request, response);
            return;
        }

        try {
            Authentication authentication = convert(request);
            if (authentication == null) {
                chain.doFilter(request, response);
                return;
            }

            Authentication authenticate = this.authenticationManager.authenticate(authentication);
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            context.setAuthentication(authenticate);
            SecurityContextHolder.setContext(context);

            securityContextRepository.saveContext(context, (HttpServletRequest) request,
                    (HttpServletResponse) response);

        } catch (Exception e) {
            ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }

    private Authentication convert(ServletRequest request) {
        try {
            Map<String, String[]> parameterMap = request.getParameterMap();
            String username = parameterMap.get("username")[0];
            String password = parameterMap.get("password")[0];

            return UsernamePasswordAuthenticationToken.unauthenticated(username, password);
        } catch (Exception e) {
            return null;
        }

    }
}
