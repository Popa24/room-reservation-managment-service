package com.example.demo.user.authentication;

import com.example.demo.user.service.UserDomainObject;
import com.example.demo.user.service.UserService;
import io.jsonwebtoken.*;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@RestController
public class LoginController {

    private static final String SECRET_KEY = "ssdjfjfjfjrfffffssdjfjfjfjrfffffssdjfjfjff3422";

    private final UserService userService;

    public LoginController(@NonNull final UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/api/login")
    public ResponseEntity<com.example.demo.user.dto.JsonLoginResponse> login(@RequestBody @NonNull final com.example.demo.user.dto.JsonLoginRequest request) {
        UserDomainObject user = userService.findByEmail(request.getEmail());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        if (user != null && passwordEncoder.matches(request.getPassword(), user.getPassword())) { // Verify the password
            String token = String.valueOf(createJWT(user.getId().toString(), user.getEmail(),user.getRoles(), 999999999)); // 24 hours in milliseconds

            return ResponseEntity.ok(new com.example.demo.user.dto.JsonLoginResponse(token));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }
    public static String createJWT(String id, String subject,String role, long ttlMillis) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        byte[] apiKeySecretBytes = Base64.getDecoder().decode(SECRET_KEY);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        JwtBuilder builder = Jwts.builder()
                .setId(id)
                .setIssuedAt(now)
                .setSubject(subject)
                .setAudience(role)
                .setIssuer("popa")
                .signWith(signingKey, signatureAlgorithm);

        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }

        return builder.compact();
    }

    public static Claims decodeJWT(String jwt) {
        byte[] apiKeySecretBytes = Base64.getDecoder().decode(SECRET_KEY);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS256.getJcaName());

        Jws<Claims> jws = Jwts.parserBuilder()
                .setSigningKey(signingKey)
                .build()
                .parseClaimsJws(jwt);

        return jws.getBody();
    }



}