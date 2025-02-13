package com.fiap.tc.infrastructure.core.security.property;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "tc-backoffice-backend-api")
@Data
public class OriginApiProperty {

    @Getter
    private final Security security = new Security();
    private String origin;


    @Setter
    @Getter
    public static class Security {

        private boolean enableHttps;

    }

}
