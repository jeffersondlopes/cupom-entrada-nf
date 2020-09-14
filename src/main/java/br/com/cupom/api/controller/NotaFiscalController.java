package br.com.cupom.api.controller;

import br.com.cupom.api.model.NotaFiscalModel;
import br.com.cupom.service.NotaFiscalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.xml.sax.SAXException;

import javax.validation.Valid;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;

@RestController
@RequestMapping("/notafiscal")
public class NotaFiscalController {

    @Autowired
    private NotaFiscalService notaFiscalService;

    @PostMapping(value = "/enviar_arquivo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public NotaFiscalModel AddNovaFiscal(@Valid NotaFiscalModel notaFiscalModel){


        try {
            NotaFiscalModel notaFiscalCliente = notaFiscalService.salvaXMlNotaFiscal(notaFiscalModel);
            return notaFiscalCliente;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }

    @GetMapping(value = "/processa_arquivo")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void AddNovaFiscal() throws Exception {

        this.notaFiscalService.processaXML();

    }

    @GetMapping("/{id}")
    public NotaFiscalModel xmlNotaFiscal(@PathVariable String id){

        return notaFiscalService.buscar(id);

    }

}
