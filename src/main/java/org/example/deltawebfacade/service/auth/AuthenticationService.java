package org.example.deltawebfacade.service.auth;

import lombok.RequiredArgsConstructor;
import org.example.deltawebfacade.dto.AuthenticationResponse;
import org.example.deltawebfacade.exceptions.ExistByEmailException;
import org.example.deltawebfacade.exceptions.LoginException;
import org.example.deltawebfacade.exceptions.NotFoundByEmailException;
import org.example.deltawebfacade.model.auth.Role;
import org.example.deltawebfacade.model.auth.Token;
import org.example.deltawebfacade.model.User;
import org.example.deltawebfacade.repository.TokenRepository;
import org.example.deltawebfacade.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TokenRepository tokenRepository;

    private void revokeAllTokenByUseId(User user) {
        List<Token> validTokenListByUserId = tokenRepository.findAllAccessTokensByUserId(user.getId());
        if (!validTokenListByUserId.isEmpty()) {
            validTokenListByUserId.forEach(token -> {
                token.setLoggedOut(true);
            });
        }
        tokenRepository.saveAll(validTokenListByUserId);
    }

    private void saveUserToken(String jwt, User user) {
        Token token = new Token();
        token.setAccessToken(jwt);
        token.setLoggedOut(false);
        token.setUser(user);
        tokenRepository.save(token);
    }

    public AuthenticationResponse register(User user) {
        if (existByEmail(user.getEmail())) {
            throw new ExistByEmailException(User.class, user.getEmail());
        }
        user.setRole(Role.USER);
        user = userRepository.save(user);
        String jwt = jwtService.generateToken(user);
        saveUserToken(jwt, user);
        return new AuthenticationResponse(jwt);
    }

    public AuthenticationResponse authenticate(User request) throws LoginException {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
        }
        catch (AuthenticationException e) {
                throw new LoginException(User.class);
        }
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new NotFoundByEmailException(User.class, request.getEmail()));
        String jwt = jwtService.generateToken(user);
        revokeAllTokenByUseId(user);
        saveUserToken(jwt, user);
        return new AuthenticationResponse(jwt);
    }

    public Boolean existByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

}
