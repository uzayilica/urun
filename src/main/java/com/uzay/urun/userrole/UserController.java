package com.uzay.urun.userrole;

import com.uzay.urun.security.Role;
import com.uzay.urun.security.RoleRepository;
import com.uzay.urun.security.User;
import com.uzay.urun.security.UserRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
public class UserController {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserController(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @PostMapping("/user-ekle")
    public ResponseEntity<?> addUser(@RequestBody User user) {
        // Önce User kaydedilir
        User savedUser = userRepository.save(user);

        // User'a gelen rolleri oluştur ve ilişkilendir
        if (user.getRole() != null && !user.getRole().isEmpty()) {
            user.getRole().forEach(role -> {
                Role newRole = new Role();
                newRole.setRoleId(role.getRoleId()); // Gelen role ID'sini al
                newRole.setRoleName(role.getRoleName());// Gelen role name  al (girerken ROLE_USER GİBİ GİR
                newRole.setRoledescription(role.getRoledescription()); // Gelen role açıklamasını al
                newRole.setUser(savedUser); // Role'ü kullanıcı ile ilişkilendir
               roleRepository.save(newRole); // Role kaydedilir
            });
        }

        // Sonuç olarak kullanıcıyı döndür
        return ResponseEntity.ok(savedUser);
    }

    @DeleteMapping("/user-sil/{userId}")
    public ResponseEntity<?> removeUser(@PathVariable Integer userId) {
        // Kullanıcının mevcut olup olmadığını kontrol et
        if (!userRepository.existsById(userId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Silinecek kullanıcı mevcut değil.");
        }

        try {
            // Kullanıcıyı sil
            userRepository.deleteById(userId);
            return ResponseEntity.ok("Kullanıcı başarıyla silindi.");
        } catch (Exception e) {
            // Hata durumunda mesaj döndür
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Bir hata oluştu: " + e.getMessage());
        }
    }

}
