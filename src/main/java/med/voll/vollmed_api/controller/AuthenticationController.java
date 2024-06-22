package med.voll.vollmed_api.controller;

import med.voll.vollmed_api.domain.user.User;
import med.voll.vollmed_api.domain.user.UserAuthenticationData;
import med.voll.vollmed_api.infra.security.TokenDataJWT;
import med.voll.vollmed_api.infra.security.TokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AuthenticationController {

    private final AuthenticationManager manager;
    private final TokenService tokenService;

    public AuthenticationController(AuthenticationManager manager, TokenService tokenService) {
        this.manager = manager;
        this.tokenService = tokenService;
    }

    @PostMapping
    public ResponseEntity<TokenDataJWT> login(@RequestBody UserAuthenticationData data) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        Authentication authentication = manager.authenticate(authenticationToken);

        String tokenJWT = tokenService.generateToken((User) authentication.getPrincipal());

        return ResponseEntity.ok(new TokenDataJWT(tokenJWT));
    }
}
