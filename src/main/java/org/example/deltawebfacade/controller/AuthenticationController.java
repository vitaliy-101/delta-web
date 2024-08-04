package org.example.deltawebfacade.controller;


import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.deltawebfacade.dto.AuthRequest;
import org.example.deltawebfacade.exceptions.LoginException;
import org.example.deltawebfacade.mapper.AuthMapper;
import org.example.deltawebfacade.dto.AuthenticationResponse;
import org.example.deltawebfacade.mapper.DtoConverter;
import org.example.deltawebfacade.model.User;
import org.example.deltawebfacade.service.auth.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "Регистрация и логин")
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final AuthMapper authMapper;
    private final DtoConverter dtoConverter;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody AuthRequest request) {
        return ResponseEntity.ok(authenticationService.register(authMapper.fromAuthRequestToUser(request)));
    }
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthRequest request) throws LoginException {
        return ResponseEntity.ok(authenticationService.authenticate(dtoConverter.simpleConvert(request, User.class)));
    }

}
