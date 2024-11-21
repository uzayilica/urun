package com.uzay.urun.resilience4jTest;

import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Test {
    @GetMapping("/resilence4jTest")
    @Retry(name = "resilence4jTest", fallbackMethod = "getFallBack")
    public String resilence4jTest() {
        // Hata fırlatarak Retry ve Fallback mekanizmasını tetikleyelim
        if (true) {
            throw new RuntimeException("Test exception");
        }
        return "resilence4jTest";
    }

    public ResponseEntity<String> getFallBack(Throwable throwable) {
        return ResponseEntity.ok("Fallback activated: " + throwable.getMessage());
    }
}


