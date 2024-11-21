package com.uzay.urun.oauth2;

import com.uzay.urun.security.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
public class OAuth2LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final MyUserDetailService myUserDetailsService;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public OAuth2LoginSuccessHandler(MyUserDetailService myUserDetailsService,
                                     UserRepository userRepository,
                                     JwtService jwtService) {
        this.myUserDetailsService = myUserDetailsService;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws ServletException, IOException {

        if (authentication instanceof OAuth2AuthenticationToken) {
            OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;

            if (token.getAuthorizedClientRegistrationId().equals("github")) {
                OAuth2User oAuth2User = token.getPrincipal();
                Map<String, Object> attributes = oAuth2User.getAttributes();

                if (!attributes.containsKey("login") || attributes.get("login") == null) {
                    throw new IllegalArgumentException("GitHub login bilgisi bulunamadı");
                }

                String login = attributes.get("login").toString();
                MyUserDetails myUserDetails;

                try {
                    myUserDetails = (MyUserDetails) myUserDetailsService.loadUserByUsername(login);
                } catch (Exception e) {
                    User newUser = new User();
                    newUser.setUsername(login);

                    Role userRole = new Role();
                    userRole.setRoleName(RoleEnum.ROLE_USER);
                    userRole.setRoledescription("Standard user role");
                    userRole.setUser(newUser);

                    Set<Role> roles = new HashSet<>();
                    roles.add(userRole);
                    newUser.setRole(roles);

                    userRepository.save(newUser);
                    myUserDetails = new MyUserDetails(newUser);
                }

                String jwtToken = jwtService.generateToken(myUserDetails);
                Cookie cookie = new Cookie("jwtToken", jwtToken);
                cookie.setHttpOnly(true);
                cookie.setSecure(true);
                cookie.setMaxAge(3600);
                cookie.setPath("/");
                response.addCookie(cookie);

                String targetUrl = determineTargetUrl(request, response, authentication);
                response.sendRedirect(targetUrl);
                return;
            }
        }

        super.onAuthenticationSuccess(request, response, authentication);
    }

    protected String determineTargetUrl(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) {
        // Başarılı giriş sonrası sabit bir URL'ye yönlendirme yap
        return "http://localhost:8080/home";
    }
}
