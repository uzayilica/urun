package com.uzay.urun.security;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class Login {


    private final MyUserDetailService myUserDetailService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public Login(MyUserDetailService myUserDetailService, PasswordEncoder passwordEncoder, UserRepository userRepository, JwtService jwtService) {
        this.myUserDetailService = myUserDetailService;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user){
        try {
            UserDetails userDetails = myUserDetailService.loadUserByUsername(user.getUsername());
            if(!passwordEncoder.matches(user.getPassword(), userDetails.getPassword())){
                return new ResponseEntity<>("Wrong password", HttpStatus.UNAUTHORIZED);
            }

            String token = jwtService.generateToken(userDetails);

            return new ResponseEntity<>(Map.of(
                    "sonuç","giriş başarılı",
                    "jwtToken",token,
                    "statusCode",HttpStatus.OK.value()
            ), HttpStatus.OK);
        }
        catch (UsernameNotFoundException e) {
            return new ResponseEntity<>("Username not found", HttpStatus.NOT_FOUND);

        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Bir hata oluştu: " + e.getMessage());
        }
    }


}
