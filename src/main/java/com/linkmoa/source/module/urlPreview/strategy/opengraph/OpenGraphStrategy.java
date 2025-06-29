package com.linkmoa.source.module.urlPreview.strategy.opengraph;

import com.linkmoa.source.module.urlPreview.common.ErrorMessages;
import com.linkmoa.source.module.urlPreview.dto.UrlPreviewResponse;
import com.linkmoa.source.module.urlPreview.strategy.UrlPreviewStrategy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

@Component
public class OpenGraphStrategy implements UrlPreviewStrategy {
    
    @Override
    public boolean supports(final String url) {
        return true; // fallback
    }

    @Override
    public UrlPreviewResponse extract(final String url) {
        try {
            Document doc = Jsoup.connect(url).timeout(3000).get();
            String title = doc.select(OpenGraphConstants.Selectors.TITLE.getSelector())
                    .attr(OpenGraphConstants.Attributes.CONTENT.getAttribute());
            String description = doc.select(OpenGraphConstants.Selectors.DESCRIPTION.getSelector())
                    .attr(OpenGraphConstants.Attributes.CONTENT.getAttribute());
            String image = doc.select(OpenGraphConstants.Selectors.IMAGE.getSelector())
                    .attr(OpenGraphConstants.Attributes.CONTENT.getAttribute());
            String siteUrl = doc.select(OpenGraphConstants.Selectors.URL.getSelector())
                    .attr(OpenGraphConstants.Attributes.CONTENT.getAttribute());

            return UrlPreviewResponse.builder()
                    .title(title)
                    .description(description)
                    .image(image)
                    .url(siteUrl)
                    .build();
        } catch (Exception e) {
            return UrlPreviewResponse.builder()
                    .title(ErrorMessages.FETCH_FAILED.getMessage())
                    .url(url)
                    .build();
        }
    }
}