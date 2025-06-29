package com.linkmoa.source.module.urlPreview;

import com.linkmoa.source.module.urlPreview.common.ErrorMessages;
import com.linkmoa.source.module.urlPreview.dto.UrlPreviewResponse;
import com.linkmoa.source.module.urlPreview.strategy.UrlPreviewStrategy;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UrlPreviewContext {
    private final List<UrlPreviewStrategy> strategies;

    public UrlPreviewContext(final List<UrlPreviewStrategy> strategies) {
        this.strategies = strategies;
    }

    public UrlPreviewResponse handle(String url) {
        return strategies.stream()
                .filter(strategy -> strategy.supports(url))
                .findFirst()
                .orElseThrow(() -> new RuntimeException(ErrorMessages.NO_STRATEGY_FOUND.getMessage()))
                .extract(url);
    }
}
