package br.com.cupom.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.DecimalMin;

@Data
@Document(collection = "xmlNotaFiscalCliente")
public class NotaFiscalCliente {

    @Id
    private String id;

    @Field("cnpjCliente")
    private Long cnpjCliente;

    @Field("xmlNotaFiscal")
    private String xmlNotaFiscal;

    @DecimalMin("1")
    private Long status = 1L;

}
