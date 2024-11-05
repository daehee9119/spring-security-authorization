package nextstep.app.configuration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class RequestMatcherEntry<T> {

    private final RequestMatcher requestMatcher;
    private final T entry;

}
