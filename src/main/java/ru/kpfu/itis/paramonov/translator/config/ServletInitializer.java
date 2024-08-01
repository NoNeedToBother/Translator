package ru.kpfu.itis.paramonov.translator.config;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import ru.kpfu.itis.paramonov.translator.TranslatorApplication;

public class ServletInitializer extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(TranslatorApplication.class);
    }
}
