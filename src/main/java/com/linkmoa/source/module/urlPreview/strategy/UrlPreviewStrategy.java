package com.linkmoa.source.module.urlPreview.strategy;

import com.linkmoa.source.module.urlPreview.dto.UrlPreviewResponse;

public interface UrlPreviewStrategy {
    boolean supports(String url);
    UrlPreviewResponse extract(String url);
}