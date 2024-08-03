package ru.kpfu.itis.paramonov.translator.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

@Configuration
@PropertySource("classpath:translate_api.properties")
public class NetworkConfig {

    @Bean
    public RestTemplate translateClient(ClientHttpRequestInterceptor headerInterceptor) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler());
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        restTemplate.getInterceptors().add(headerInterceptor);

        return restTemplate;
    }

    private static final String API_HOST_HEADER = "x-rapidapi-host";
    private static final String API_KEY_HEADER = "x-rapidapi-key";

    @Bean
    public ClientHttpRequestInterceptor headerInterceptor(
            @Value("${rapid.api.host}") String apiHost,
            @Value("${rapid.api.key}") String apiKey
    ) {
        return (request, body, execution) -> {
            HttpHeaders headers = request.getHeaders();
            headers.add(API_HOST_HEADER, apiHost);
            headers.add(API_KEY_HEADER, apiKey);
            return execution.execute(request, body);
        };
    }
}
