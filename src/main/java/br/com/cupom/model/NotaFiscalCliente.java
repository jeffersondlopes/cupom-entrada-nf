package br.com.cupom.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
@Document(collection = "xmlNotaFiscalCliente")
public class NotaFiscalCliente {

    @MongoId
    private String id;

    @Field("cnpjCliente")
    private Long cnpjCliente;

    @Field("xmlNotaFiscal")
    @JsonIgnore
    private String xmlNotaFiscal;

}
