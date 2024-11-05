package nextstep.security.authentication;

import java.util.Set;

public interface Authentication {

    Object getCredentials();

    Object getPrincipal();

    Set<String> getRoles();

    boolean isAuthenticated();
}
