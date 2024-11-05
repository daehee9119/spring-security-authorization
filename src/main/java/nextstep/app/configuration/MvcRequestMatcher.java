package nextstep.app.configuration;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;

@RequiredArgsConstructor
@Getter
public class MvcRequestMatcher implements RequestMatcher {

    private final HttpMethod httpMethod;
    private final String pattern;

    @Override
    public boolean matches(HttpServletRequest request) {
        if (Objects.isNull(httpMethod) || !request.getMethod().equalsIgnoreCase(httpMethod.toString())) {
            return false;
        }
        return request.getRequestURI().matches(pattern);
    }
}
