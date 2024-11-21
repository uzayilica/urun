package com.uzay.urun.csrftoken;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CstfTokenController {

    @GetMapping("/get-csrf-token")
    public CsrfToken getCsrfToken(HttpServletRequest servletRequest) {
        CsrfToken csrfToken = (CsrfToken) servletRequest.getAttribute(CsrfToken.class.getName());
        return csrfToken;

    }

}
