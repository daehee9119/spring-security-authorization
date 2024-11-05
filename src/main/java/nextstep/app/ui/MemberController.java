package nextstep.app.ui;

import nextstep.security.authorization.Secured;
import nextstep.app.domain.Member;
import nextstep.app.domain.MemberRepository;
import nextstep.app.exception.UserNotFoundException;
import nextstep.security.authentication.AuthenticationException;
import nextstep.security.authorization.ForbiddenException;
import nextstep.security.context.SecurityContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MemberController {

    private final MemberRepository memberRepository;

    public MemberController(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @GetMapping("/members")
    public ResponseEntity<List<Member>> list() {
        List<Member> members = memberRepository.findAll();
        return ResponseEntity.ok(members);
    }

    @GetMapping("/members/me")
    public ResponseEntity<Member> getMyInformation() {
        String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        return ResponseEntity.ok(memberRepository.findByEmail(username).orElseThrow(UserNotFoundException::new));
    }

    @Secured(role = "ADMIN")
    @GetMapping("/search")
    public ResponseEntity<List<Member>> search() {
        return ResponseEntity.ok(memberRepository.findAll());
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Void> handleAuthenticationException() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Void> handleUserNotFoundException() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<Void> handleForbiddenException() {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
}
