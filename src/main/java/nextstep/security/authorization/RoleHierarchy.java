package nextstep.security.authorization;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import nextstep.security.authentication.AuthenticationException;

@RequiredArgsConstructor
public class RoleHierarchy {

    private final String rule;

    public boolean check(String requestAuthority, String needAuthority) {
        List<String> rules = Arrays.stream(rule.split(">"))
                .map(String::trim)
                .collect(Collectors.toList());
        if (rules.contains(requestAuthority) && rules.contains(needAuthority)) {
            return rules.indexOf(requestAuthority) <= rules.indexOf(needAuthority);
        }
        throw new AuthenticationException();
    }
}
