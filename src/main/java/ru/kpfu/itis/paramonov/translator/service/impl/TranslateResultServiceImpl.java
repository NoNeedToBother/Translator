package ru.kpfu.itis.paramonov.translator.service.impl;

import ru.kpfu.itis.paramonov.translator.dto.TranslateResultDto;
import ru.kpfu.itis.paramonov.translator.exceptions.DatabaseException;
import ru.kpfu.itis.paramonov.translator.mappers.TranslateResultMapper;
import ru.kpfu.itis.paramonov.translator.repository.TranslateResultRepository;
import ru.kpfu.itis.paramonov.translator.service.TranslateResultService;

import java.sql.SQLException;

public class TranslateResultServiceImpl implements TranslateResultService {

    private final TranslateResultRepository translateResultRepository;

    private final TranslateResultMapper translateResultMapper;

    public TranslateResultServiceImpl(TranslateResultRepository translateResultRepository,
                                      TranslateResultMapper translateResultMapper) {
        this.translateResultRepository = translateResultRepository;
        this.translateResultMapper = translateResultMapper;
    }

    private static final String SAVE_TRANSLATE_RESULT_ERROR = "Failed to save translate result";

    @Override
    public void save(TranslateResultDto translateResultDto) {
        try {
            translateResultRepository.save(
                    translateResultMapper.fromDto(translateResultDto)
            );
        } catch (SQLException e) {
            throw new DatabaseException(SAVE_TRANSLATE_RESULT_ERROR);
        }
    }
}
