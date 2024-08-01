package ru.kpfu.itis.paramonov.translator.mappers;

import ru.kpfu.itis.paramonov.translator.dto.TranslateResultDto;
import ru.kpfu.itis.paramonov.translator.model.TranslateResult;

public class TranslateResultMapper {

    public TranslateResult fromDto(TranslateResultDto dto) {
        return new TranslateResult(
                dto.getIpAddress(), dto.getOriginalText(), dto.getTranslatedText()
        );
    }

    public TranslateResultDto fromModel(TranslateResult model) {
        return new TranslateResultDto(
                model.getIpAddress(), model.getOriginalText(), model.getTranslatedText()
        );
    }
}
