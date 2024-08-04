package ru.kpfu.itis.paramonov.translator.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.kpfu.itis.paramonov.translator.dto.TranslateResultDto;
import ru.kpfu.itis.paramonov.translator.dto.request.TranslateRequestDto;
import ru.kpfu.itis.paramonov.translator.exceptions.DatabaseException;
import ru.kpfu.itis.paramonov.translator.service.TranslateResultService;
import ru.kpfu.itis.paramonov.translator.service.TranslationService;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest
public class TranslateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TranslationService translationService;

    @MockBean
    private TranslateResultService translateResultService;

    @MockBean
    private HttpServletRequest request;

    @Test
    public void testTranslation() throws Exception {
        given(translationService.translate("ru", "en", "Привет мир"))
                .willReturn("Hello world");
        when(request.getRemoteAddr()).thenReturn("127.0.0.1");
        TranslateResultDto translateResultDto = new TranslateResultDto("127.0.0.1", "Привет мир", "Hello world");
        doNothing().when(translateResultService).save(translateResultDto);

        TranslateRequestDto translateRequestDto = new TranslateRequestDto("ru", "en", "Привет мир");

        mockMvc.perform(post("/translate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(translateRequestDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.result").value("Hello world"))
                .andExpect(jsonPath("$.databaseSaveSuccess").value(true));
    }

    @Test
    public void testTranslationDatabaseError() throws Exception {
        given(translationService.translate("ru", "en", "Привет мир"))
                .willReturn("Hello world");
        when(request.getRemoteAddr()).thenReturn("127.0.0.1");

        doThrow(new DatabaseException("Error")).when(translateResultService).save(argThat(dto ->
                dto.getIpAddress().equals("127.0.0.1") &&
                        dto.getOriginalText().equals("Привет мир") &&
                        dto.getTranslatedText().equals("Hello world")
        ));

        TranslateRequestDto translateRequestDto = new TranslateRequestDto("ru", "en", "Привет мир");

        mockMvc.perform(post("/translate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(translateRequestDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.result").value("Hello world"))
                .andExpect(jsonPath("$.databaseSaveSuccess").value(false));

    }

}
