package ru.kpfu.itis.paramonov.translator.dto.request;

import lombok.Getter;

@Getter
public class TranslateRequestDto {

    private String languageFrom;

    private String languageTo;

    private String text;

}
