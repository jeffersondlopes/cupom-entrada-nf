package br.com.cupom.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
@Document(collection = "XML_NOTA_FISCAL_CLIENTE")
public class NotaFiscalCliente {

    @MongoId
    private String id;

    @Field("cnpjCliente")
    private Long cnpjCliente;

    @Field("xmlNotaFiscal")
    private String xmlNotaFiscal;

}
