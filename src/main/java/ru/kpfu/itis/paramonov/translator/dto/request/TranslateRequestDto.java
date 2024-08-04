package ru.kpfu.itis.paramonov.translator.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TranslateRequestDto {

    private String languageFrom;

    private String languageTo;

    private String text;

}
