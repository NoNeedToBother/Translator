package ru.kpfu.itis.paramonov.translator.controller;

import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.kpfu.itis.paramonov.translator.dto.TranslateResultDto;
import ru.kpfu.itis.paramonov.translator.dto.request.TranslateRequestDto;
import ru.kpfu.itis.paramonov.translator.dto.response.TranslateResultResponseDto;
import ru.kpfu.itis.paramonov.translator.exceptions.DatabaseException;
import ru.kpfu.itis.paramonov.translator.service.TranslateResultService;
import ru.kpfu.itis.paramonov.translator.service.TranslationService;

@RestController
@RequiredArgsConstructor
public class TranslateController {

    private final TranslationService translationService;

    private final TranslateResultService translateResultService;

    @PostMapping("/translate")
    public ResponseEntity<TranslateResultResponseDto> translate(
            HttpServletRequest request,
            @RequestBody TranslateRequestDto translateRequestDto) {
        String result = translationService.translate(
                translateRequestDto.getLanguageFrom(),
                translateRequestDto.getLanguageTo(),
                translateRequestDto.getText()
        );
        boolean success;
        try {
            translateResultService.save(new TranslateResultDto(
                    request.getRemoteAddr(), translateRequestDto.getText(), result
            ));
            success = true;
        } catch (DatabaseException e) {
            success = false;
        }

        return new ResponseEntity<>(new TranslateResultResponseDto(result, success), HttpStatus.OK);
    }
}
