package com.zeldaspeedruns.zeldaspeedruns.dialect;

import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.account.SimpleKeycloakAccount;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.engine.AttributeName;
import org.thymeleaf.expression.IExpressionObjects;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractAttributeTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.templatemode.TemplateMode;

import javax.servlet.http.HttpServletRequest;

public class KeycloakTokenAttributeTagProcessor extends AbstractAttributeTagProcessor {
    private static final String NAME = "token";
    private static final int PRECEDENCE = 1000;

    public KeycloakTokenAttributeTagProcessor(final String dialectPrefix) {
        super(TemplateMode.HTML, dialectPrefix, null, false, NAME, true, PRECEDENCE, true);
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
            var principal = (KeycloakPrincipal<KeycloakSecurityContext>) account.getPrincipal();
            var idToken = principal.getKeycloakSecurityContext().getIdToken();

            switch (value) {
                case "preferredUsername":
                    structureHandler.setBody(idToken.getPreferredUsername(), true);
                    break;

                case "subject":
                    structureHandler.setBody(idToken.getSubject(), true);
                    break;

                default:
                    break;
            }
        }
    }
}
