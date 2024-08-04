package org.example.deltawebfacade.service.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.example.deltawebfacade.model.User;
import org.example.deltawebfacade.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtService {
     private final String SECRET_KEY = "4bb6d1dfbafb64a681139d1586b6f1160d18159afd57c8c79136d7490630407c";
     private final TokenRepository tokenRepository;
     private Claims extractAllClaims(String token) {
          return Jwts
                  .parser()
                  .verifyWith(getSigninKey())
                  .build()
                  .parseSignedClaims(token)
                  .getPayload();
     }

     private SecretKey getSigninKey() {
          byte[] keyBytes = Decoders.BASE64URL.decode(SECRET_KEY);
          return Keys.hmacShaKeyFor(keyBytes);
     }

     private boolean isTokenExpired(String token) {
          return extractExpiration(token).before(new Date());
     }

     private Date extractExpiration(String token) {
          return extractClaim(token, Claims::getExpiration);
     }

     public String generateToken(User user) {
         return Jwts
                  .builder()
                  .subject(user.getEmail())
                  .issuedAt(new Date(System.currentTimeMillis()))
                  .expiration(new Date(System.currentTimeMillis() + 24*60*60*1000)) //время истечения срока токена
                  .signWith(getSigninKey())
                  .compact();
     }

     public <T> T extractClaim(String token, Function<Claims, T> resolver) {
          Claims claims = extractAllClaims(token);
          return resolver.apply(claims);
     }

     public String extractUsername(String token) {
          return extractClaim(token, Claims::getSubject);
     }

     public boolean isValid(String token, UserDetails user) {
          String username = extractUsername(token);
          boolean validToken = tokenRepository
                  .findByAccessToken(token)
                  .map(t -> !t.isLoggedOut())
                  .orElse(false);

          return (username.equals(user.getUsername())) && !isTokenExpired(token) && validToken;
     }

}
