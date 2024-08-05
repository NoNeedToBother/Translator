package ru.kpfu.itis.paramonov.translator.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ru.kpfu.itis.paramonov.translator.model.TranslateResult;
import ru.kpfu.itis.paramonov.translator.repository.impl.TranslateResultRepositoryImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
public class TranslateResultRepositoryTest {

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private TranslateResult translateResult;

    private TranslateResultRepository translateResultRepository;

    @BeforeEach
    public void setUp() throws Exception {
        translateResultRepository = new TranslateResultRepositoryImpl(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
    }

    @Test
    public void testSave() throws Exception {
        TranslateResult translateResult = new TranslateResult("127.0.0.1", "Hello world", "Привет мир");
        translateResultRepository.save(translateResult);

        verify(preparedStatement).setString(1, "127.0.0.1");
        verify(preparedStatement).setString(2, "Hello world");
        verify(preparedStatement).setString(3, "Привет мир");
        verify(preparedStatement).executeUpdate();
        verify(preparedStatement).close();
    }

    @Test
    public void testSaveError() throws Exception {
        doThrow(SQLException.class).when(preparedStatement).executeUpdate();

        assertThrows(SQLException.class, () -> translateResultRepository.save(translateResult));
    }
}
