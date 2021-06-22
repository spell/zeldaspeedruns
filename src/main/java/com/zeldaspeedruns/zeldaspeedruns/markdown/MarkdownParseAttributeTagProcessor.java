package com.zeldaspeedruns.zeldaspeedruns.markdown;

import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.engine.AttributeName;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractAttributeTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.standard.expression.StandardExpressions;
import org.thymeleaf.templatemode.TemplateMode;

public class MarkdownParseAttributeTagProcessor extends AbstractAttributeTagProcessor {
    private static final String NAME = "parse";
    private static final int PRECEDENCE = 1000;
    private final Parser parser;
    private final HtmlRenderer renderer;

    public MarkdownParseAttributeTagProcessor(final String dialectPrefix,
                                              final Parser parser,
                                              final HtmlRenderer renderer) {
        super(TemplateMode.HTML, dialectPrefix, null, false, NAME, true, PRECEDENCE, true);
        this.parser = parser;
        this.renderer = renderer;
    }

    @Override
    protected void doProcess(final ITemplateContext context,
                             final IProcessableElementTag tag,
                             final AttributeName name,
                             final String value,
                             final IElementTagStructureHandler structureHandler) {
        final var configuration = context.getConfiguration();
        final var expressionParser = StandardExpressions.getExpressionParser(configuration);
        final var expression = expressionParser.parseExpression(context, value);
        final var markdown = (String) expression.execute(context);

        var document = parser.parse(markdown);
        structureHandler.setBody(renderer.render(document), false);
    }
}
