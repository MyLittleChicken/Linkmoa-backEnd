package com.linkmoa.source.module.urlPreview;

import com.linkmoa.source.module.urlPreview.dto.UrlPreviewRequest;
import com.linkmoa.source.module.urlPreview.dto.UrlPreviewResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UrlPreviewService {
    private final UrlPreviewContext context;

    public UrlPreviewResponse getPreview(final UrlPreviewRequest request) {
        return context.handle(request.getUrl());
    }
}
