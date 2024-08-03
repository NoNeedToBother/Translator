package ru.kpfu.itis.paramonov.translator.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class TranslateResultDto {

    private final String ipAddress;
    
    private final String originalText;
    
    private final String translatedText;

}
