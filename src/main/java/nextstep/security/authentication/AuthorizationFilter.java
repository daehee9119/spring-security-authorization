package nextstep.security.authentication;

import java.io.IOException;
import java.util.Objects;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nextstep.security.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import static nextstep.security.utils.URLConstants.MEMBERS_REQUEST_URI;

public class AuthorizationFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        Authentication registeredAuthentication = SecurityContextHolder.getContext().getAuthentication();

        if (Objects.isNull(registeredAuthentication)) {
            ((HttpServletResponse) servletResponse).setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        HttpServletRequest request = (HttpServletRequest) servletRequest;

        if (request.getRequestURI().equals(MEMBERS_REQUEST_URI) && !registeredAuthentication.getRoles().contains(
                "ADMIN")) {
            ((HttpServletResponse) servletResponse).setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
