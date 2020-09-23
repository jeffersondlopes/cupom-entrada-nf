package br.com.cupom.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Data
@JsonPropertyOrder({"cProd",
        "cEAN", "xProd","NCM","CEST","CFOP",
        "uCom", "qCom","vUnCom","vProd","cEANTrib",
        "uTrib", "qTrib","vUnTrib","vDesc",
        "indTot", "nItemPed"})
public class Produto {

    @JsonProperty("cProd")
    private String cProd;

    @JsonProperty("cEAN")
    private String cEAN;

    @JsonProperty("xProd")
    private String xProd;

    @JsonProperty("NCM")
    private String NCM;

    @JsonProperty("CEST")
    private Long CEST;

    @JsonProperty("CFOP")
    private String CFOP;

    @JsonProperty("uCom")
    private String uCom;

    @JsonProperty("qCom")
    private BigDecimal qCom;

    @JsonProperty("vUnCom")
    private BigDecimal  vUnCom;

    @JsonProperty("vProd")
    private BigDecimal  vProd;

    @JsonProperty("cEANTrib")
    private String cEANTrib;

    @JsonProperty("uTrib")
    private String uTrib;

    @JsonProperty("qTrib")
    private BigDecimal qTrib;

    @JsonProperty("vUnTrib")
    private BigDecimal vUnTrib;

    @JsonProperty("vDesc")
    private BigDecimal vDesc;

    @JsonProperty("indTot")
    private Integer indTot;

    @JsonProperty("nItemPed")
    @PositiveOrZero
    private Integer nItemPed;

    @JsonIgnore
    private String message;
}
