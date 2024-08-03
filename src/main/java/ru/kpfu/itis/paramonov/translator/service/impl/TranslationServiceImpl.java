package ru.kpfu.itis.paramonov.translator.service.impl;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.kpfu.itis.paramonov.translator.service.TranslationService;

@Service
@PropertySource("classpath:translate_api.properties")
@RequiredArgsConstructor
public class TranslationServiceImpl implements TranslationService {

    @Value("${rapid.api.url}") private String rapidApiUrl;

    private final RestTemplate translateClient;

    private static final String ORIGINAL_LANGUAGE_QUERY_PARAM = "source_lang";
    private static final String TARGET_LANGUAGE_QUERY_PARAM = "target_lang";


    @Override
    public String translate(String originalLang, String targetLang, String text) {
        String uri = UriComponentsBuilder.fromUriString(rapidApiUrl)
                .queryParam(ORIGINAL_LANGUAGE_QUERY_PARAM, originalLang)
                .queryParam(TARGET_LANGUAGE_QUERY_PARAM, targetLang)
                .build().toUriString();

        HttpEntity<TranslateRequest> requestHttpEntity = new HttpEntity<>(new TranslateRequest(text));

        ResponseEntity<TranslateResponse> response =
                translateClient.exchange(uri, HttpMethod.POST, requestHttpEntity, TranslateResponse.class);
        if (response.getBody() != null) {
            return response.getBody().data.targetText;
        } else throw new RuntimeException();
    }

    @Getter
    @AllArgsConstructor
    private static class TranslateRequest {

        private String sourceText;

    }

    @NoArgsConstructor
    @Setter
    private static class TranslateResponse {
        private TranslateData data;
    }

    @NoArgsConstructor
    @Setter
    private static class TranslateData {
        private String targetText;

        private String targetLanguage;

    }
}
