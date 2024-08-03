package ru.kpfu.itis.paramonov.translator.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kpfu.itis.paramonov.translator.dto.response.TranslateResultResponseDto;
import ru.kpfu.itis.paramonov.translator.service.TranslationService;

@RestController
@RequiredArgsConstructor
public class TranslateController {

    private final TranslationService translationService;

    @GetMapping("/translate")
    public ResponseEntity<TranslateResultResponseDto> translate() {
        String res = translationService.translate("en", "ru", "Hello world!");
        return new ResponseEntity<>(new TranslateResultResponseDto(res), HttpStatus.OK);
    }
}
