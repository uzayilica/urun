package com.uzay.urun.config;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.util.List;
import java.util.Map;

@ConfigurationProperties(prefix = "degiskenler")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
@Builder
public class ConfigProperty {

    private String isim;

    private String password;

     private List<String>yetenek;

     private Map<String,String> vucut;

}
