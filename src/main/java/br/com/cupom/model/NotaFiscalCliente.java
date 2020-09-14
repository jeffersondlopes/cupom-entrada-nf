package br.com.cupom.model;

import br.com.cupom.utils.notafiscal.TNfeProc;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.DecimalMin;
import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "xmlNotaFiscalCliente")
public class NotaFiscalCliente {

    @Id
    private String id;

    @Field("cnpjCliente")
    private Long cnpjCliente;

    @Field("pathXmlServer")
    private String pathXmlServer;

    @Field("xmlNotaFiscal")
    private String xml;

    @DecimalMin("1")
    private Long status = 1L;

    @Field("mensagensErro")
    private List<String> mensagensErro = new ArrayList<>();

    @Transient
    private boolean erro = false;

    @Transient
    private TNfeProc tNfeProc;

}
