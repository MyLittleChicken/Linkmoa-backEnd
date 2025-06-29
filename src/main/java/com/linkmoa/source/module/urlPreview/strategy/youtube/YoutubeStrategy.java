package com.linkmoa.source.module.urlPreview.strategy.youtube;

import com.linkmoa.source.module.urlPreview.dto.UrlPreviewResponse;
import com.linkmoa.source.module.urlPreview.strategy.UrlPreviewStrategy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class YoutubeStrategy implements UrlPreviewStrategy {
    
    private static final Pattern YOUTUBE_PATTERN = Pattern.compile(YoutubeConstants.URL_PATTERN);

    @Override
    public boolean supports(final String url) {
        return YOUTUBE_PATTERN.matcher(url).find();
    }

    @Override
    public UrlPreviewResponse extract(final String url) {
        Matcher matcher = YOUTUBE_PATTERN.matcher(url);
        if (matcher.find()) {
            String videoId = matcher.group(1);
            String apiUrl = YoutubeConstants.BASE_URL + videoId;
            try {
                Document doc = Jsoup.connect(apiUrl).timeout(3000).get();
                String title = doc.title();
                String thumbnail = YoutubeConstants.THUMBNAIL_BASE_URL + videoId + YoutubeConstants.THUMBNAIL_SUFFIX;
                return UrlPreviewResponse.builder()
                        .title(title)
                        .image(thumbnail)
                        .url(apiUrl)
                        .build();
            } catch (Exception e) {
                return UrlPreviewResponse.builder()
                        .title(YoutubeConstants.ErrorMessages.FETCH_FAILED.getMessage())
                        .url(url)
                        .build();
            }
        }
        return UrlPreviewResponse.builder()
                .title(YoutubeConstants.ErrorMessages.INVALID_URL.getMessage())
                .url(url)
                .build();
    }
}