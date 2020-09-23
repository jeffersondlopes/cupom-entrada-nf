package br.com.cupom.service.rest;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
public class StatusServiceRest {

    private String url;
    private String method;
    private HttpStatus status;
    private String title;
    private String message;
    private String classe;

}
