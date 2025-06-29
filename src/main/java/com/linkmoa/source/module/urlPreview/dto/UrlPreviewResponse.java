package com.linkmoa.source.module.urlPreview.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UrlPreviewResponse {
    @Builder.Default
    private String title = "";
    @Builder.Default
    private String description = "";
    @Builder.Default
    private String image = "";
    @Builder.Default
    private String url = "";
}
