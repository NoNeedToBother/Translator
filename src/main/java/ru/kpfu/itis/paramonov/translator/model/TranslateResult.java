package ru.kpfu.itis.paramonov.translator.model;

public class TranslateResult {

    private final String ipAddress;

    private final String originalText;

    private final String translatedText;

    public TranslateResult(String ipAddress, String originalText, String translatedText) {
        this.ipAddress = ipAddress;
        this.originalText = originalText;
        this.translatedText = translatedText;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public String getOriginalText() {
        return originalText;
    }

    public String getTranslatedText() {
        return translatedText;
    }
}
