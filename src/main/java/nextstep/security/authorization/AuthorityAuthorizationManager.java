package nextstep.security.authorization;

import java.util.Objects;

import lombok.RequiredArgsConstructor;
import nextstep.security.authentication.Authentication;
import nextstep.security.authentication.AuthenticationException;

// 주어진
@RequiredArgsConstructor
public class AuthorityAuthorizationManager<T> implements AuthorizationManager<T> {

    private final RoleHierarchy roleHierarchy;
    private final String needAuthority;

    @Override
    public AuthorizationDecision check(Authentication authentication, T object) {
        if (Objects.isNull(authentication)) {
            throw new AuthenticationException();
        }

        boolean isGranted =
                authentication.getRoles().stream().anyMatch(userAuthority -> roleHierarchy.check(userAuthority,
                        needAuthority));

        return new AuthorizationDecision(isGranted);
    }

}
