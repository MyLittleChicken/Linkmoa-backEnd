package com.linkmoa.source.module.urlPreview.strategy.opengraph;

import lombok.Getter;

public final class OpenGraphConstants {
    
    @Getter
    public enum Selectors {
        TITLE("meta[property=og:title]"),
        DESCRIPTION("meta[property=og:description]"),
        IMAGE("meta[property=og:image]"),
        URL("meta[property=og:url]");

        private final String selector;

        Selectors(final String selector) {
            this.selector = selector;
        }
    }
    
    @Getter
    public enum Attributes {
        CONTENT("content");

        private final String attribute;

        Attributes(final String attribute) {
            this.attribute = attribute;
        }
    }
}