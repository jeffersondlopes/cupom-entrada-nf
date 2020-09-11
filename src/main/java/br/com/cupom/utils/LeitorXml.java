package br.com.cupom.utils;

import br.com.cupom.api.model.Produto;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
@Data
public class LeitorXml {

    private final String path = "nfeProc/NFe/infNFe/det/prod";

    private List<Produto> produtoList;

    private NodeList geraNodeList(InputStream isXml) throws ParserConfigurationException, IOException, SAXException, XPathExpressionException {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(isXml);

        XPath xPath = XPathFactory.newInstance().newXPath();
        XPathExpression xPathExpression = xPath.compile(path);

        NodeList produtosNota = (NodeList) xPathExpression.evaluate(document, XPathConstants.NODESET);

        return produtosNota;

    }

    private String getConteudoTagXml(Element itemProd, String tagXMl) {
        NodeList tagName = itemProd.getElementsByTagName(tagXMl);
        Element item = (Element) tagName.item(0);
        if (item != null){
            return item.getTextContent();
        }
        return "0";
    }

    private void geraListaProdutos(NodeList produtosNota) {

        produtoList = new ArrayList<>();

        for (int i = 0; i < produtosNota.getLength(); i++) {

            Element itemProduto = (Element) produtosNota.item(i);
            Produto produto = new Produto();

            produto.setCProd(getConteudoTagXml(itemProduto, "cProd"));
            produto.setCEAN(getConteudoTagXml(itemProduto, "cEAN"));
            produto.setXProd(getConteudoTagXml(itemProduto, "xProd"));
            produto.setNCM(getConteudoTagXml(itemProduto, "NCM"));
            produto.setCEST(Long.parseLong(getConteudoTagXml(itemProduto, "CEST")));
            produto.setCFOP(Long.parseLong(getConteudoTagXml(itemProduto, "CFOP")));
            produto.setUCom(getConteudoTagXml(itemProduto, "uCom"));
            produto.setQCom(new BigDecimal(getConteudoTagXml(itemProduto, "qCom")));
            produto.setCProd(getConteudoTagXml(itemProduto, "vUnCom"));
            produto.setVProd(new BigDecimal(getConteudoTagXml(itemProduto, "vProd")));
            produto.setCEANTrib(getConteudoTagXml(itemProduto, "cEANTrib"));
            produto.setUTrib(getConteudoTagXml(itemProduto, "uTrib"));
            produto.setQTrib(new BigDecimal(getConteudoTagXml(itemProduto, "qTrib")));
            produto.setVUnTrib(new BigDecimal(getConteudoTagXml(itemProduto, "vUnTrib")));
            produto.setIndTot(Integer.parseInt(getConteudoTagXml(itemProduto, "indTot")));
            System.out.println("==================================");

            produtoList.add(produto);
        }

    }

    public void processaMxl(InputStream isXml) throws Exception {


        NodeList nodeList = geraNodeList(isXml);

        geraListaProdutos(nodeList);

        System.out.println(nodeList.getLength());


    }

}
