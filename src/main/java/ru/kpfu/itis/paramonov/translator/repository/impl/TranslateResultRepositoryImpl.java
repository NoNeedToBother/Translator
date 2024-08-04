package ru.kpfu.itis.paramonov.translator.repository.impl;

import org.springframework.stereotype.Repository;
import ru.kpfu.itis.paramonov.translator.model.TranslateResult;
import ru.kpfu.itis.paramonov.translator.repository.TranslateResultRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Repository
public class TranslateResultRepositoryImpl implements TranslateResultRepository {

    private static final String DB_IP_FIELD_NAME = "ip";

    private static final String DB_ORIGINAL_TEXT_FIELD_NAME = "original_text";

    private static final String DB_TRANSLATED_TEXT_FIELD_NAME = "translated_text";

    private static final String DB_TRANSLATE_RESULT_TABLE_NAME = "translate_results";

    private final Connection connection;

    public TranslateResultRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void save(TranslateResult translateResult) throws SQLException {
        String sql = String.format(
                "insert into %s(%s, %s, %s) values (?, ?, ?)",
                DB_TRANSLATE_RESULT_TABLE_NAME,
                DB_IP_FIELD_NAME, DB_ORIGINAL_TEXT_FIELD_NAME, DB_TRANSLATED_TEXT_FIELD_NAME
        );

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, translateResult.getIpAddress());
            statement.setString(2, translateResult.getOriginalText());
            statement.setString(3, translateResult.getTranslatedText());

            statement.executeUpdate();
        }
    }
}
