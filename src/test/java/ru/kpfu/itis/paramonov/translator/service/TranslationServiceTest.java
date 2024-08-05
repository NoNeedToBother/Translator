package ru.kpfu.itis.paramonov.translator.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;
import ru.kpfu.itis.paramonov.translator.exceptions.TranslationException;
import ru.kpfu.itis.paramonov.translator.service.impl.TranslationServiceImpl;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest()
public class TranslationServiceTest {

    @MockBean
    private RestTemplate restTemplate;

    private TranslationService translationService;

    @Value("${rapid.api.url}") private String rapidApiUrl;

    @BeforeEach
    public void setUp() {
        translationService = new TranslationServiceImpl(restTemplate);
        try {
            Field urlField = TranslationServiceImpl.class.getDeclaredField("rapidApiUrl");
            urlField.setAccessible(true);
            urlField.set(translationService, rapidApiUrl);
        } catch (NoSuchFieldException | IllegalAccessException ignored) {}
    }

    @Test
    public void testTranslate() throws Exception {
        Class<?> responseClass = Class.forName("ru.kpfu.itis.paramonov.translator.service.impl.TranslationServiceImpl$TranslateResponse");
        Constructor<?> responseConstructor = responseClass.getDeclaredConstructor();
        responseConstructor.setAccessible(true);
        Object response = responseConstructor.newInstance();

        Class<?> translateDataResponseClass = Class.forName("ru.kpfu.itis.paramonov.translator.service.impl.TranslationServiceImpl$TranslateData");
        Constructor<?> translateDataResponseConstructor = translateDataResponseClass.getDeclaredConstructor();
        translateDataResponseConstructor.setAccessible(true);
        Object translateData = translateDataResponseConstructor.newInstance();

        Field targetTextDataField = translateDataResponseClass.getDeclaredField("targetText");
        targetTextDataField.setAccessible(true);
        targetTextDataField.set(translateData, "Hello");

        Field translateDataResponseField = responseClass.getDeclaredField("data");
        translateDataResponseField.setAccessible(true);
        translateDataResponseField.set(response, translateData);

        ResponseEntity entity = new ResponseEntity<>(responseClass.cast(response), HttpStatus.OK);

        when(restTemplate.exchange(eq(rapidApiUrl), eq(HttpMethod.POST), any(HttpEntity.class), eq(responseClass))).thenReturn(entity);

        String test = translationService.translate("ru", "en", "Привет");
        verify(restTemplate).exchange(eq(rapidApiUrl), eq(HttpMethod.POST), any(HttpEntity.class), eq(responseClass));

        assertEquals("Hello", test);
    }

    @Test
    public void testTranslationError() throws Exception {
        Class<?> responseClass = Class.forName("ru.kpfu.itis.paramonov.translator.service.impl.TranslationServiceImpl$TranslateResponse");
        doThrow(RuntimeException.class).when(restTemplate).exchange(eq(rapidApiUrl), eq(HttpMethod.POST), any(HttpEntity.class), eq(responseClass));

        TranslationException ex = assertThrows(TranslationException.class, () -> translationService.translate("ru", "en", "Привет"));
        String expectedMessage = "Error occurred when translating, try again later";
        assertEquals(expectedMessage, ex.getMessage());
    }

}
