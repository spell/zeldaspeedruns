package com.zeldaspeedruns.zeldaspeedruns.dialect;

import org.springframework.stereotype.Component;
import org.thymeleaf.dialect.AbstractProcessorDialect;
import org.thymeleaf.processor.IProcessor;

import java.util.Set;

@Component
public class KeycloakDialect extends AbstractProcessorDialect {
    public final static String NAME = "Keycloak Security Dialect";
    public final static String PREFIX = "keycloak";
    private final static int PRECEDENCE = 1000;

    public KeycloakDialect() {
        super(NAME, PREFIX, PRECEDENCE);
    }

    @Override
    public Set<IProcessor> getProcessors(String dialectPrefix) {
        return Set.of(
                new KeycloakLinkAttributeTagProcessor(dialectPrefix),
                new KeycloakTokenAttributeTagProcessor(dialectPrefix)
        );
    }
}
