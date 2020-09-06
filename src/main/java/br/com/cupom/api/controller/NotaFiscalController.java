package br.com.cupom.api.controller;

import br.com.cupom.api.model.NotaFiscalModel;
import br.com.cupom.model.NotaFiscalCliente;
import br.com.cupom.service.NotaFiscalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/notafiscal")
public class NotaFiscalController {


    @Autowired
    private NotaFiscalService notaFiscalService;

    @PostMapping(value = "/arquivo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public NotaFiscalCliente AddNovaFiscal(@Valid NotaFiscalModel notaFiscalModel){

        NotaFiscalCliente notaFiscalCliente = null;

        try {
            notaFiscalCliente = notaFiscalService.salvaXMlNotaFiscal(notaFiscalModel);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return notaFiscalCliente;
        
    }

}
