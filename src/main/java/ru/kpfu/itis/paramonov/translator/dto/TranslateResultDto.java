package ru.kpfu.itis.paramonov.translator.dto;

public class TranslateResultDto {

    private final String ipAddress;
    
    private final String originalText;
    
    private final String translatedText;

    public TranslateResultDto(String ipAddress, String originalText, String translatedText) {
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
