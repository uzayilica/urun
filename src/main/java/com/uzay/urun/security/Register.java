package com.uzay.urun.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

@RestController
public class Register {

    private static final Logger logger = LoggerFactory.getLogger(Register.class);

    private final MyUserDetailService myUserDetailService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public Register(MyUserDetailService myUserDetailService, PasswordEncoder passwordEncoder, UserRepository userRepository, RoleRepository roleRepository) {
        this.myUserDetailService = myUserDetailService;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        try {
            // Kullanıcı adı zaten varsa kontrol et
            try {
                UserDetails existingUser = myUserDetailService.loadUserByUsername(user.getUsername());
                if (existingUser != null) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body("Bu kullanıcı adı zaten alınmış.");
                }
            } catch (UsernameNotFoundException e) {
                // Eğer kullanıcı bulunamazsa, bu demek oluyor ki kullanıcı adı kullanılmıyor
                // Devam et
            }

            // Şifreyi encode et
            user.setPassword(passwordEncoder.encode(user.getPassword()));

            // Kullanıcı rolünü ayarla
            Role userRole = new Role();
            userRole.setRoleName(RoleEnum.ROLE_USER);
            userRole.setUser(user);

            Set<Role> roles = new HashSet<>();
            roles.add(userRole);
            user.setRole(roles);

            // Kullanıcıyı kaydet
            userRepository.save(user);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Kullanıcı başarıyla oluşturuldu");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Bir hata oluştu: " + e.getMessage());
        }
    }
}
