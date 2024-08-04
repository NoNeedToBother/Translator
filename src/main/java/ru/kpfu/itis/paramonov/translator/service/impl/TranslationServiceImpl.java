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
import ru.kpfu.itis.paramonov.translator.exceptions.TranslationException;
import ru.kpfu.itis.paramonov.translator.service.TranslationService;

import java.util.*;
import java.util.concurrent.*;

@Service
@PropertySource("classpath:translate_api.properties")
@RequiredArgsConstructor
public class TranslationServiceImpl implements TranslationService {

    @Value("${rapid.api.url}") private String rapidApiUrl;

    private final RestTemplate translateClient;

    private static final String ORIGINAL_LANGUAGE_QUERY_PARAM = "source_lang";
    private static final String TARGET_LANGUAGE_QUERY_PARAM = "target_lang";

    private static final int MAX_TRANSLATION_THREAD_AMOUNT = 100;
    private static final long REQUEST_TIMEOUT = 10000L;

    private static final String TIMEOUT_ERROR = "Getting translation takes too long, try again later";
    private static final String TRANSLATION_ERROR = "Error occurred when translating, try again later";


    @Override
    public String translate(String originalLang, String targetLang, String text) {
        String uri = UriComponentsBuilder.fromUriString(rapidApiUrl)
                .queryParam(ORIGINAL_LANGUAGE_QUERY_PARAM, originalLang)
                .queryParam(TARGET_LANGUAGE_QUERY_PARAM, targetLang)
                .build().toUriString();

        String[] words = text.split(" ");
        ExecutorService executorService = Executors.newFixedThreadPool(MAX_TRANSLATION_THREAD_AMOUNT);

        String result;
        try  {
            result = String.join(" ", processTranslations(words, uri, executorService));
        } finally {
            executorService.shutdown();
        }
        if (result.isEmpty() && words.length > 0) throw new TranslationException(TRANSLATION_ERROR);
        else return result;
    }

    private List<String> processTranslations(String[] words, String uri, ExecutorService executorService) {
        List<Future<String>> futures = Arrays.stream(words)
                .map(word -> executorService.submit(() -> translateWord(uri, word)))
                .toList();

        return futures.stream()
                .map(future -> {
                    try {
                        return future.get(REQUEST_TIMEOUT, TimeUnit.MILLISECONDS);
                    } catch (TimeoutException e) {
                        throw new TranslationException(TIMEOUT_ERROR);
                    } catch (InterruptedException | ExecutionException e) {
                        throw new TranslationException(TRANSLATION_ERROR);
                    }
                })
                .toList();
    }

    private String translateWord(String uri, String word) {
        HttpEntity<TranslateRequest> requestHttpEntity = new HttpEntity<>(new TranslateRequest(word));

        ResponseEntity<TranslateResponse> response =
                translateClient.exchange(uri, HttpMethod.POST, requestHttpEntity, TranslateResponse.class);
        if (response.getBody() != null) {
            return response.getBody().data.targetText;
        } else throw new TranslationException(TRANSLATION_ERROR);
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
