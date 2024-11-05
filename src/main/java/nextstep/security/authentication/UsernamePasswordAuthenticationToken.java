package nextstep.security.authentication;

import java.util.Set;

public class UsernamePasswordAuthenticationToken implements Authentication {

    private final Object principal;
    private final Object credentials;
    private final Set<String> roles;
    private final boolean authenticated;

    private UsernamePasswordAuthenticationToken(Object principal, Object credentials,
            Set<String> roles, boolean authenticated) {
        this.principal = principal;
        this.credentials = credentials;
        this.roles = roles;
        this.authenticated = authenticated;
    }

    public static UsernamePasswordAuthenticationToken unauthenticated(String principal, String credentials) {
        return new UsernamePasswordAuthenticationToken(principal, credentials, null,false);
    }


    public static UsernamePasswordAuthenticationToken authenticated(String principal, String credentials,
            Set<String> roles) {
        return new UsernamePasswordAuthenticationToken(principal, credentials, roles, true);
    }

    @Override
    public Object getCredentials() {
        return credentials;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

    @Override
    public Set<String> getRoles() {
        return roles;
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }
}
