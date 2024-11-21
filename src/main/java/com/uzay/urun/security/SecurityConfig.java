package com.uzay.urun.security;

import com.uzay.urun.oauth2.OAuth2LoginSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

@Configuration
public class SecurityConfig {

    @Autowired
    MyUserDetailService myUserDetailService;

    @Autowired
    OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;

    @Autowired
    JwtService jwtService;

    @Autowired
    JwtFilter jwtFilter;



    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }




    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(myUserDetailService);
        return provider;
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // Spesifik originleri belirtin
        configuration.setAllowedOrigins(Arrays.asList(
                "http://localhost:5173",     // React development server
                "http://localhost:8080",     // Your frontend domain
                "https://yourdomain.com"     // Production domain
        ));

        // Credentials'a izin ver
        configuration.setAllowCredentials(true);

        // İzin verilen HTTP metodları
        configuration.setAllowedMethods(Arrays.asList(
                "GET",
                "POST",
                "PUT",
                "DELETE",
                "OPTIONS"
        ));

        // İzin verilen headerlar
        configuration.setAllowedHeaders(Arrays.asList(
                "Authorization",
                "Content-Type",
                "Accept",
                "Origin",
                "X-Requested-With"
        ));

        // Response headerlarına izin ver
        configuration.setExposedHeaders(Arrays.asList(
                "Authorization"
        ));

        // Preflight request cache süresi (saniye)
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .csrf(csrf->csrf.disable())
                .cors(cors->cors.configurationSource(corsConfigurationSource()))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // JWT filter'ı UsernamePasswordAuthenticationFilter'dan önce ekleyin
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(request->request
//                        .requestMatchers("/user-ozel").hasRole("USER")
//                        .requestMatchers("/admin-ozel").hasAuthority("ROLE_ADMIN")
//                        .requestMatchers(
//                                "/register",
//                                "/get-csrf-token",
//                                "/login",
//                                "/login2",
//                                "/customLoginPage",
//                                "/oauth2/callback/github",
//                                "/login/oauth2/code/github",
//                                "/error", // Hata sayfası için
//                                "/home",
//                                "/webjars/**", // Static kaynaklar için
//                                "/static/**",
//                                "/css/**",
//                                "/js/**",
//                                "/giris-yapmis-kisi-bilgisi-1",
//                                "/giris-yapmis-kisi-bilgisi-2",
//                                "/urun-service/**"
//                        ).permitAll()
//                        .anyRequest().authenticated())
                        .anyRequest().permitAll())
                .oauth2Login(oauth2->oauth2
                        .loginPage("/customLoginPage")
                        .successHandler(oAuth2LoginSuccessHandler)

                )
                .logout(logout -> logout
                        .logoutUrl("/cikis")
                        .logoutSuccessUrl("/deneme")
                        .deleteCookies("JSESSIONID", "jwtToken")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                )
                .build();
    }




}
