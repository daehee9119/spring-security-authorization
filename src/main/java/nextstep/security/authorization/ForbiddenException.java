package nextstep.security.authorization;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class ForbiddenException extends RuntimeException {
    public ForbiddenException() {
        super("권한이 없는 사용자 입니다.");
    }

    public ForbiddenException(String message) {
        super(message);
    }
}
