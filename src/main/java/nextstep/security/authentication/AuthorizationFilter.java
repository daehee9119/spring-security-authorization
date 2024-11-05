package nextstep.security.authentication;

import java.io.IOException;
import java.util.Objects;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;
import nextstep.security.authorization.AuthorizationDecision;
import nextstep.security.authorization.AuthorizationManager;
import nextstep.security.authorization.ForbiddenException;
import nextstep.security.authorization.RequestAuthorizationManager;
import nextstep.security.authorization.RoleHierarchy;
import nextstep.security.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;

import static nextstep.security.utils.URLConstants.MEMBERS_REQUEST_URI;

@RequiredArgsConstructor
public class AuthorizationFilter extends OncePerRequestFilter {

    private final AuthorizationManager<HttpServletRequest> authorizationManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        try{
            Authentication registeredAuthentication = SecurityContextHolder.getContext().getAuthentication();
            AuthorizationDecision authorizationDecision = authorizationManager.check(registeredAuthentication, request);

            if (Objects.isNull(authorizationDecision) || !authorizationDecision.isGranted()) {
                throw new ForbiddenException();
            }
        } catch (ForbiddenException forbiddenException) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        } catch (AuthenticationException authenticationException) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        filterChain.doFilter(request, response);
    }
}
