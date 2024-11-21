package com.uzay.urun.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class Authentication {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;

    @PostMapping("/login2")  // register2 yerine login olarak değiştirildi
    public ResponseEntity<?> login(@RequestBody User user) {
        try {
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    user.getUsername(),
                    user.getPassword()
            );
            authenticationManager.authenticate(
                    authentication
            );
//*            authentication.isAuthenticated()'ı kaldırmanızın çalışmasını sağlamasının nedeni, Spring Security'nin authenticate()
//?            işlemi zaten kimlik doğrulamanın başarılı olduğunu garanti ederken, isAuthenticated() kontrolünün gereksiz veya
//?            yanlış bir şekilde false dönebilmesidir.
            String token = jwtService.generateToken2(authentication);
            return ResponseEntity.ok()
                    .body(Map.of(
                            "token", token,
                            "username", user.getUsername()
                    ));

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Geçersiz kullanıcı adı veya şifre");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Giriş işlemi sırasında bir hata oluştu");
        }
    }
}

//!veya 2. yol buda çalışıyor
//@PostMapping("/login2")  // register2 yerine login olarak değiştirildi
//public ResponseEntity<?> login(@RequestBody User user) {
//    UsernamePasswordAuthenticationToken authenticate = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
//    org.springframework.security.core.Authentication authenticated =  myAuthenticationProvider.authenticate(authenticate);
//    if (authenticated != null) {
//        String token = jwtService.generateToken2(authenticated);
//        return ResponseEntity.ok().body(token);
//    }
//    return  ResponseEntity.badRequest().body("bir hata oluştu");
//}}