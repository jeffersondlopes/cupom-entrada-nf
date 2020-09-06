package br.com.cupom.api.controller;

import br.com.cupom.api.model.NotaFiscalModel;
import br.com.cupom.model.NotaFiscalCliente;
import br.com.cupom.repository.NotaFsicalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/notafiscal")
public class NotaFiscalController {

    @Autowired
    private NotaFsicalRepository repository;

    @PostMapping(value = "/arquivo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public NotaFiscalCliente AddNovaFiscal(@Valid NotaFiscalModel notaFiscalModel){
        NotaFiscalCliente notaFiscalCliente = new NotaFiscalCliente();
        notaFiscalCliente.setCnpjCliente(12345685L);
        notaFiscalCliente.setXmlNotaFiscal("<xml>asdfadf</xml>");
        NotaFiscalCliente save = repository.save(notaFiscalCliente);
        System.out.println(save);
        return save;
    }

}
