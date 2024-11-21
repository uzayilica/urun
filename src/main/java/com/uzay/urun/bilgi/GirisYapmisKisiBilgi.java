package com.uzay.urun.bilgi;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class GirisYapmisKisiBilgi {

    @GetMapping("/giris-yapmis-kisi-bilgisi-1")
    public ResponseEntity<?> getGirisYapmisKullaniciBilgisi(@AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        String password = userDetails.getPassword();
        List<? extends GrantedAuthority> authorities = userDetails.getAuthorities().stream().toList();

        List<String> authority = authorities.stream().map(var -> var.getAuthority()).toList();


        return ResponseEntity.ok().body(
                Map.of("username",username,"password",password,"authorities", authorities,"authority",authority)
        );

    }
    @GetMapping("/giris-yapmis-kisi-bilgisi-2")
    public ResponseEntity<?> getGirisYapmisKullaniciBilgisi2(@AuthenticationPrincipal OAuth2User oAuth2User) {

        return ResponseEntity.ok().body(oAuth2User.getName());

    }
    }


