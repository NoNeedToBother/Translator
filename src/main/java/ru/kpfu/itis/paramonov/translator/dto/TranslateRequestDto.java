package ru.kpfu.itis.paramonov.translator.dto;

public class TranslateRequestDto {

    private final String languageFrom;

    private final String languageTo;

    private final String text;

    public TranslateRequestDto(String languageFrom, String languageTo, String text) {
        this.languageFrom = languageFrom;
        this.languageTo = languageTo;
        this.text = text;
    }

    public String getLanguageFrom() {
        return languageFrom;
    }

    public String getLanguageTo() {
        return languageTo;
    }

    public String getText() {
        return text;
    }
}
