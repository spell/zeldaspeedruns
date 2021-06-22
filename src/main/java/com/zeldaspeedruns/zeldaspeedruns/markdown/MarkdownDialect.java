package com.zeldaspeedruns.zeldaspeedruns.markdown;

import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.stereotype.Component;
import org.thymeleaf.dialect.AbstractProcessorDialect;
import org.thymeleaf.processor.IProcessor;

import java.util.Set;

@Component
public class MarkdownDialect extends AbstractProcessorDialect {
    public final static String NAME = "Markdown Dialect";
    public final static String PREFIX = "md";
    private final static int PRECEDENCE = 1000;

    private final Parser parser;
    private final HtmlRenderer renderer;

    public MarkdownDialect() {
        super(NAME, PREFIX, PRECEDENCE);
        parser = Parser.builder().build();
        renderer = HtmlRenderer.builder().build();
    }

    @Override
    public Set<IProcessor> getProcessors(String dialectPrefix) {
        return Set.of(
                new MarkdownParseAttributeTagProcessor(dialectPrefix, parser, renderer)
        );
    }
}
