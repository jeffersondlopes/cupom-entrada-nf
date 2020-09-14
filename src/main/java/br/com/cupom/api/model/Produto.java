package br.com.cupom.api.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@JsonPropertyOrder({"cProd",
        "cEAN",
        "xProd",
        "NCM",
        "CEST",
        "CFOP",
        "uCom",
        "qCom",
        "vUnCom",
        "vProd",
        "cEANTrib",
        "uTrib",
        "qTrib",
        "vUnTrib",
        "vDesc",
        "indTot",
        "nItemPed"})
public class Produto {

    private String cProd;

    private String cEAN;

    private String xProd;

    private String NCM;

    private Long CEST;

    private Long CFOP;

    private String uCom;

    private BigDecimal qCom;

    private BigDecimal  vUnCom;

    private BigDecimal  vProd;

    private String cEANTrib;

    private String uTrib;

    private BigDecimal qTrib;

    private BigDecimal vUnTrib;

    private BigDecimal vDesc;

    private Integer indTot;

    private Integer nItemPed;

}
