package org.example.deltawebfacade.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.example.deltawebfacade.dto.UserResponse;
import org.example.deltawebfacade.service.auth.JwtService;
import org.example.deltawebfacade.service.auth.UserDetailsServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Tag(name = "Информация о пользователе")
public class UserController {

    private final JwtService jwtService;
    private final UserDetailsServiceImpl UserDetailsServiceImpl;
    private final UserDetailsServiceImpl userDetailsServiceImpl;

    @GetMapping("/user")
    @Operation(summary = "Получить информацию о пользователе по токену")
    public ResponseEntity<UserResponse> getUserInfo(@RequestHeader("Authorization") String token){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(username);
        UserResponse userResponse = new UserResponse(userDetails.getUsername());
        return ResponseEntity.ok(userResponse);
    }

}
