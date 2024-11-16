package com.uzay.urun.audit;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import org.springframework.stereotype.Component;

import java.util.Optional;
@Component("AuditAwareConfigs")

public class AuditAwareConfigs implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        // Burada kimlik doğrulama veya kullanıcı bilgilerini sağlayabilirsiniz
        // SecurityContextHolder ile mevcut kullanıcıyı alabilirsiniz
        return Optional.of("admin");
//        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication().getName());
    }
}
