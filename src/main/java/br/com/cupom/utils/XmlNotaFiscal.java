package br.com.cupom.utils;

import br.com.cupom.api.assembler.ProdutoDissambler;
import br.com.cupom.api.model.Produto;
import br.com.cupom.model.NotaFiscalCliente;
import br.com.cupom.service.CadastroProdutoService;
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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@Data
public class XmlNotaFiscal {

    private Path pathXml = Paths.get("/home/jefferson/lixo");

    @Autowired
    private ProdutoDissambler produtoDissambler;

    @Autowired
    private CadastroProdutoService cadastroProdutoService;

    public String salvarArquivoXml(MultipartFile file) throws IOException {

        String uuid = UUID.randomUUID().toString().concat(".xml");
        Path resolve = this.pathXml.resolve(uuid);
        Files.copy(file.getInputStream(), resolve);
        return resolve.toString();

    }

    private TNfeProc xmlJaxb(String xml) throws RuntimeMBeanException {

        try {
            ByteArrayInputStream is = new ByteArrayInputStream(xml.getBytes());
            JAXBContext jaxbContext = JAXBContext.newInstance(TNfeProc.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            TNfeProc tNfeProc = (TNfeProc) unmarshaller.unmarshal(is);
            return tNfeProc;
        } catch (JAXBException ex) {
            throw new RuntimeException("Erro ao fazer parse do XML. O mesmo está inválido");
        } catch (Exception ex) {
            throw new RuntimeException("Erro ao abrir o arquivo: " + ex.getMessage());
        }

    }

    public void parseNotaFiscal(NotaFiscalCliente notaFiscalCliente) {
        try {
            TNfeProc tNfeProc = xmlJaxb(notaFiscalCliente.getXml());
            notaFiscalCliente.setTNfeProc(tNfeProc);
            notaFiscalCliente.setProdutoList(geraListaProdutos(notaFiscalCliente));
            notaFiscalCliente.setStatus(2);
        } catch (RuntimeException ex) {
            notaFiscalCliente.getMensagensErro().add(ex.getMessage());
            notaFiscalCliente.setStatus(99);
            notaFiscalCliente.setErro(true);
            notaFiscalCliente.setTNfeProc(null);
        }
    }

    public void envioProdutoNova(TNfeProc nfeProc) {

        String cnpjEmitente = nfeProc.getNFe().getInfNFe().getEmit().getCNPJ();

        List<TNFe.InfNFe.Det> detalheNotaList = nfeProc.getNFe().getInfNFe().getDet();

        List<Produto> produtos = detalheNotaList.stream()
                .map(prodNfe -> produtoDissambler.map(prodNfe.getProd()))
                .collect(Collectors.toList());

        cadastroProdutoService.cadastrarProdutos(produtos);

    }

    public List<Produto> geraListaProdutos(List<NotaFiscalCliente> listaNotasFiscais) {

        List<NotaFiscalCliente> nfsSemErro = listaNotasFiscais.stream()
                .filter(nfe -> !nfe.isErro())
                .collect(Collectors.toList());

        List<TNFe.InfNFe.Det.Prod> produtosNf = new ArrayList<>();

        nfsSemErro.forEach(nfe -> {
            List<TNFe.InfNFe.Det> detList = nfe.getTNfeProc().getNFe().getInfNFe().getDet();
            List<TNFe.InfNFe.Det.Prod> prods = detList.stream().map(det -> det.getProd()).collect(Collectors.toList());
            produtosNf.addAll(prods);
        });

        List<Produto> produtoList = produtosNf.stream()
                .map(prod -> produtoDissambler.map(prod)).collect(Collectors.toList());

        return produtoList;

    }

    public List<Produto> geraListaProdutos(NotaFiscalCliente notaFiscalCliente) {

        List<TNFe.InfNFe.Det> detList = notaFiscalCliente.getTNfeProc().getNFe().getInfNFe().getDet();
        List<TNFe.InfNFe.Det.Prod> prods = detList.stream().map(det -> det.getProd()).collect(Collectors.toList());

        List<Produto> produtoList = prods.stream()
                .filter(prod -> prod.getCEAN().matches("[0-9]*"))
                .map(prod -> produtoDissambler.map(prod)).collect(Collectors.toList());

        return produtoList;

    }


}
