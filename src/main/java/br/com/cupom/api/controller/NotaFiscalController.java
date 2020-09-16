package br.com.cupom.api.controller;

import br.com.cupom.api.model.NotaFiscalModel;
import br.com.cupom.service.NotaFiscalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/notafiscal")
public class NotaFiscalController {

    @Autowired
    private NotaFiscalService notaFiscalService;

    @PostMapping(value = "/enviar_arquivo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public NotaFiscalModel AddNovaFiscal(@Valid NotaFiscalModel notaFiscalModel) {

        try {
            NotaFiscalModel notaFiscalCliente = notaFiscalService.salvaXMlNFMongoDB(notaFiscalModel);
            return notaFiscalCliente;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }

    @GetMapping("/{id}")
    public NotaFiscalModel ConsultaNotaFiscalMongoDB(@PathVariable String id){
        return notaFiscalService.buscar(id);
    }

    @GetMapping(value = "/processa_arquivo")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void AddNovaFiscal() throws Exception {
        this.notaFiscalService.cadastrarProdutos();
    }

}
