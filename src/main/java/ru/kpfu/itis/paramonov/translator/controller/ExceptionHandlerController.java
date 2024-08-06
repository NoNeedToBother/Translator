package ru.kpfu.itis.paramonov.translator.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.kpfu.itis.paramonov.translator.dto.response.BaseResponseDto;
import ru.kpfu.itis.paramonov.translator.exceptions.BadRequestException;
import ru.kpfu.itis.paramonov.translator.exceptions.TranslationException;

@ControllerAdvice
@RestController
public class ExceptionHandlerController {

    private static final String STANDARD_ERROR_MESSAGE = "Something went wrong, try again later";

    @ExceptionHandler(TranslationException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public BaseResponseDto handleTranslationException(TranslationException e) {
        return new BaseResponseDto(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public BaseResponseDto handleException() {
        return new BaseResponseDto(STANDARD_ERROR_MESSAGE);
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BaseResponseDto handleBadRequestException(BadRequestException e) {
        return new BaseResponseDto(e.getMessage());
    }
}
