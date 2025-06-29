package com.linkmoa.source.module.urlPreview.strategy.youtube;

import lombok.Getter;

public final class YoutubeConstants {
    
    public static final String URL_PATTERN = "^(?:https?://)?(?:www\\.)?(?:youtube\\.com/watch\\?v=|youtu\\.be/)([\\w-]{11})";
    public static final String BASE_URL = "https://www.youtube.com/watch?v=";
    public static final String THUMBNAIL_BASE_URL = "https://img.youtube.com/vi/";
    public static final String THUMBNAIL_SUFFIX = "/hqdefault.jpg";
    
    @Getter
    public enum ErrorMessages {
        FETCH_FAILED("Failed to fetch YouTube"),
        INVALID_URL("Invalid YouTube URL");

        private final String message;

        ErrorMessages(final String message) {
            this.message = message;
        }
    }
}