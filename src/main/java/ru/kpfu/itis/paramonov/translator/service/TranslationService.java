package ru.kpfu.itis.paramonov.translator.service;

public interface TranslationService {

    String translate(String originalLang, String targetLang, String text);
}
