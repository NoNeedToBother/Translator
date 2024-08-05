package ru.kpfu.itis.paramonov.translator.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.kpfu.itis.paramonov.translator.dto.TranslateResultDto;
import ru.kpfu.itis.paramonov.translator.exceptions.DatabaseException;
import ru.kpfu.itis.paramonov.translator.mappers.TranslateResultMapper;
import ru.kpfu.itis.paramonov.translator.model.TranslateResult;
import ru.kpfu.itis.paramonov.translator.repository.TranslateResultRepository;
import ru.kpfu.itis.paramonov.translator.service.impl.TranslateResultServiceImpl;

import java.sql.SQLException;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TranslateResultServiceTest {

    @MockBean
    private TranslateResultRepository translateResultRepository;

    @InjectMocks
    private TranslateResultMapper translateResultMapper;

    private TranslateResultService translateResultService;

    @Mock
    private TranslateResultDto translateResultDto;

    @BeforeEach
    public void setUp() {
        translateResultService = new TranslateResultServiceImpl(translateResultRepository, translateResultMapper);
    }

    @Test
    public void testSave() throws Exception {
        doNothing().when(translateResultRepository).save(any(TranslateResult.class));
    }

    @Test
    public void testSaveError() throws Exception {
        doThrow(SQLException.class).when(translateResultRepository).save(any(TranslateResult.class));

        assertThrows(DatabaseException.class, () -> translateResultService.save(translateResultDto));
    }
}
