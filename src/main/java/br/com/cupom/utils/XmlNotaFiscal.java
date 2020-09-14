package br.com.cupom.utils;

import br.com.cupom.api.assembler.ProdutoDissambler;
import br.com.cupom.api.model.Produto;
import br.com.cupom.model.NotaFiscalCliente;
import br.com.cupom.repository.NotaFsicalRepository;
import br.com.cupom.utils.notafiscal.TNFe;
import br.com.cupom.utils.notafiscal.TNfeProc;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.management.RuntimeMBeanException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@Data
public class XmlNotaFiscal {

    private Path pathXml = Paths.get("/home/jefferson/lixo");

    @Autowired
    private NotaFsicalRepository notaFsicalRepository;

    @Autowired
    private ProdutoDissambler produtoDissambler;

    public String salvarArquivoXml(MultipartFile file) throws IOException {

        String uuid = UUID.randomUUID().toString().concat(".xml");
        Path resolve = this.pathXml.resolve(uuid);
        Files.copy(file.getInputStream(), resolve);
        return resolve.toString();

    }

    private Object xmlJaxb(InputStream is)  {

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(TNfeProc.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            TNfeProc tNfeProc = (TNfeProc) unmarshaller.unmarshal(is);
            return tNfeProc;
        } catch (JAXBException ex){
            return "Erro no parse: " + ex.getMessage();
        } catch (Exception ex){
            return "Erro ao abrir " + ex.getMessage();
        }

    }

    private TNfeProc xmlJaxb(String xml) throws RuntimeMBeanException {

        try {
            ByteArrayInputStream is = new ByteArrayInputStream(xml.getBytes());
            JAXBContext jaxbContext = JAXBContext.newInstance(TNfeProc.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            TNfeProc tNfeProc = (TNfeProc) unmarshaller.unmarshal(is);
            return tNfeProc;
        } catch (Exception ex){
            throw new RuntimeException("Erro ao abrir o arquivo: " + ex.getMessage());
        }

    }

    private void parseNotaFiscal(NotaFiscalCliente notaFiscalCliente){
        try {
            TNfeProc tNfeProc = xmlJaxb(notaFiscalCliente.getXml());
            notaFiscalCliente.setTNfeProc(tNfeProc);
        } catch (RuntimeException ex) {
            notaFiscalCliente.setErro(true);
            notaFiscalCliente.setTNfeProc(null);
        }
    }

    private void geraListaProduto(TNfeProc nfeProc){

        String cnpjEmitente = nfeProc.getNFe().getInfNFe().getEmit().getCNPJ();

        List<TNFe.InfNFe.Det> detalheNotaList = nfeProc.getNFe().getInfNFe().getDet();

        List<Produto> produtos = detalheNotaList.stream()
                .map(prodNfe -> produtoDissambler.map(prodNfe.getProd()))
                .collect(Collectors.toList());

        produtos.forEach(produto -> System.out.println(produto));
    }

    public TNfeProc main() throws IOException, JAXBException {

        List<NotaFiscalCliente> notaFiscalClientes = notaFsicalRepository.findByStatus(1L);

        notaFiscalClientes.forEach(p -> {
            parseNotaFiscal(p);
            geraListaProduto(p.getTNfeProc());
            //notaFsicalRepository.save(p);
        });

        return null;

    }


}
