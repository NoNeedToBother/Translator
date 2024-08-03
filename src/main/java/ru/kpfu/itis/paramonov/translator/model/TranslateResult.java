package ru.kpfu.itis.paramonov.translator.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class TranslateResult {

    private final String ipAddress;

    private final String originalText;

    private final String translatedText;

}
