package ru.kpfu.itis.paramonov.translator.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@AllArgsConstructor
public class TranslateResultResponseDto extends BaseResponseDto {

    private String result;

}
