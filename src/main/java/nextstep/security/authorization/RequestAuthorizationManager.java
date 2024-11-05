package nextstep.security.authorization;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import lombok.RequiredArgsConstructor;
import nextstep.app.configuration.RequestMatcher;
import nextstep.app.configuration.RequestMatcherEntry;
import nextstep.security.authentication.Authentication;
import nextstep.security.utils.URLConstants;

@RequiredArgsConstructor
public class RequestAuthorizationManager implements AuthorizationManager<HttpServletRequest> {

    private final List<RequestMatcherEntry<AuthorizationManager>> mappings;

    private final List<String> allowedEndpoints = List.of(URLConstants.DEFAULT_REQUEST_URI, URLConstants.SEARCH_URI);

    @Override
    public AuthorizationDecision check(Authentication authentication, HttpServletRequest request) {
        for (RequestMatcherEntry<AuthorizationManager> requestMatcherEntry : mappings) {
            RequestMatcher requestMatcher = requestMatcherEntry.getRequestMatcher();
            if (requestMatcher.matches(request)) {
                AuthorizationManager authorizationManager = requestMatcherEntry.getEntry();
                return authorizationManager.check(authentication, request);
            }
        }
        return new AuthorizationDecision(true);
    }

}
