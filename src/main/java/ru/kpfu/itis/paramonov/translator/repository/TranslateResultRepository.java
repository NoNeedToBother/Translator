package ru.kpfu.itis.paramonov.translator.repository;

import ru.kpfu.itis.paramonov.translator.model.TranslateResult;

import java.sql.SQLException;

public interface TranslateResultRepository {

    void save(TranslateResult translateResult) throws SQLException;

}
