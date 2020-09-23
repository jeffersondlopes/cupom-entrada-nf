package br.com.cupom.api.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Problem {

    private Integer status;
    private OffsetDateTime timestamp;
    private String type;
    private String title;
    private String detail;
    private String userMessage;

}
