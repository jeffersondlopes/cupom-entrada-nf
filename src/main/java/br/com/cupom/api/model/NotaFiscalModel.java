package br.com.cupom.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class NotaFiscalModel {

    @NotNull
    private MultipartFile arquivo;

    @JsonProperty("cnpjCliente")
    @NotNull
    private Long cnpjCliente;

}
