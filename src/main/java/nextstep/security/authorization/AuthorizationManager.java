package nextstep.security.authorization;

import nextstep.security.authentication.Authentication;

public interface AuthorizationManager<T> {

    AuthorizationDecision check(Authentication authentication, T object);

}
