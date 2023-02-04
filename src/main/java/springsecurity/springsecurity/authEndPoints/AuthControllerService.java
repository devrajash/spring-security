package springsecurity.springsecurity.authEndPoints;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import springsecurity.springsecurity.config.JwtService;
import springsecurity.springsecurity.repository.UserRepository;
import springsecurity.springsecurity.user.User;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class AuthControllerService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public AuthResponse register(AuthRegisterReq request) {

        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        repository.save(user);
        var jwtToken = jwtService.genarateTokenWithPayload(user);
        return AuthResponse.builder()
                .token(jwtToken)
                .username(user.getUsername())
                .build();
    }

    public AuthResponse login(AuthReqUser request) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                request.getUsername(), request.getPassword(), getAuthorities());
        try {
            var x = authenticationManager.authenticate(authenticationToken);
            var user = repository.findByUsername(request.getUsername())
                    .orElseThrow(() -> new UsernameNotFoundException("no user found"));

            return AuthResponse.builder()
                    .username(user.getUsername())
                    .token(jwtService.genarateTokenWithPayload(user))
                    .build();
        } catch (Exception exception) {
            return AuthResponse.builder().msg(exception.getMessage()).build();
        }

    }

    Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

}
