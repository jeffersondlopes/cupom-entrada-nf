package br.com.cupom.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NotaFiscalModel {

    @JsonIgnoreProperties(allowGetters = true)
    private String id;

    @NotNull
    private MultipartFile arquivo;

    @JsonProperty("cnpjCliente")
    @NotNull
    private Long cnpjCliente;

    @JsonIgnoreProperties(allowGetters = true)
    private String xmlNotaFiscal;

}
