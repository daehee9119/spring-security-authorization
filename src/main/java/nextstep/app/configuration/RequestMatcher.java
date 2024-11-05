package nextstep.app.configuration;

import javax.servlet.http.HttpServletRequest;

public interface RequestMatcher {

    boolean matches(HttpServletRequest request);

}
