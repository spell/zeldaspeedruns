package com.zeldaspeedruns.zeldaspeedruns.dialect;

import org.keycloak.adapters.springsecurity.account.SimpleKeycloakAccount;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.common.util.KeycloakUriBuilder;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.engine.AttributeName;
import org.thymeleaf.expression.IExpressionObjects;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractAttributeTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.templatemode.TemplateMode;

import javax.servlet.http.HttpServletRequest;

public class KeycloakLinkAttributeTagProcessor extends AbstractAttributeTagProcessor {
    private static final String NAME = "link";
    private static final int PRECEDENCE = 1000;

    public KeycloakLinkAttributeTagProcessor(final String dialectPrefix) {
        super(TemplateMode.HTML, dialectPrefix, "a", false, NAME, true, PRECEDENCE, true);
    }

    @Override
    protected void doProcess(final ITemplateContext context,
                             final IProcessableElementTag tag,
                             final AttributeName name,
                             final String value,
                             final IElementTagStructureHandler structureHandler) {
        final IExpressionObjects expressionObjects = context.getExpressionObjects();
        final HttpServletRequest request = (HttpServletRequest) expressionObjects.getObject("request");
        final KeycloakAuthenticationToken token = (KeycloakAuthenticationToken) request.getUserPrincipal();

        // If we cannot retrieve the token, we do nothing
        if (token != null) {
            final var account = (SimpleKeycloakAccount) token.getDetails();
            final var deployment = account.getKeycloakSecurityContext().getDeployment();

            if (value.equals("account")) {
                structureHandler.setAttribute("href", KeycloakUriBuilder.fromUri(deployment.getAccountUrl())
                        .queryParam("referrer", deployment.getResourceName())
                        .toTemplate());
            }
        }
    }
}
