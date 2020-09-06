package br.com.cupom.service;

import br.com.cupom.api.model.NotaFiscalModel;
import br.com.cupom.model.NotaFiscalCliente;
import br.com.cupom.repository.NotaFsicalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

@Service
public class NotaFiscalService {

    @Autowired
    private NotaFsicalRepository repository;

    private String LeituraXml(InputStream inputStream){

        var xml = new StringBuffer();
        Scanner scanner = new Scanner(inputStream);

        while(scanner.hasNextLine()){
            xml.append(scanner.nextLine()
                    .replace("\t","")
                    .replace("\n",""));
        }

        return xml.toString().trim();
    }

    public NotaFiscalCliente salvaXMlNotaFiscal(NotaFiscalModel notaFiscalModel) throws IOException {
        String xml = this.LeituraXml(notaFiscalModel.getArquivo().getInputStream());
        NotaFiscalCliente notaFiscalCliente = new NotaFiscalCliente();
        notaFiscalCliente.setCnpjCliente(notaFiscalModel.getCnpjCliente());
        notaFiscalCliente.setXmlNotaFiscal(xml);
        return repository.save(notaFiscalCliente);
    }
}
