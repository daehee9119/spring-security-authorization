package nextstep.security.authorization;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AuthorizationDecision {

    private final boolean granted;

    public boolean isGranted() {
        return granted;
    }

}
