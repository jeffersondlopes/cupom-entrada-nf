package br.com.cupom.service;

import br.com.cupom.api.assembler.NotaFiscalDissambler;
import br.com.cupom.api.exception.EntidadeNaoEncontradaException;
import br.com.cupom.api.model.NotaFiscalModel;
import br.com.cupom.api.model.Produto;
import br.com.cupom.model.NotaFiscalCliente;
import br.com.cupom.repository.NotaFsicalRepository;
import br.com.cupom.utils.XmlNotaFiscal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Scanner;

@Service
public class NotaFiscalService {

    @Autowired
    private NotaFsicalRepository repository;

    @Autowired
    private NotaFiscalDissambler notaFiscalDissambler;

    @Autowired
    private XmlNotaFiscal xmlNotaFiscal;

    @Autowired
    private NotaFsicalRepository notaFsicalRepository;

    @Autowired
    private CadastroProdutoService cadastroProdutoService;

    private String LeituraXml(InputStream inputStream) {

        var xml = new StringBuffer();
        Scanner scanner = new Scanner(inputStream);

        while (scanner.hasNextLine()) {
            xml.append(scanner.nextLine()
                    .replace("\t", "")
                    .replace("\n", ""));
        }

        return xml.toString().trim();
    }

    @Transactional
    public NotaFiscalModel salvaXMlNFMongoDB(NotaFiscalModel notaFiscalModel) throws IOException {

        String xml = this.LeituraXml(notaFiscalModel.getArquivo().getInputStream());
        NotaFiscalCliente notaFiscalCliente = notaFiscalDissambler.ModelToDomain(notaFiscalModel);
        String pathArquivoXml = xmlNotaFiscal.salvarArquivoXml(notaFiscalModel.getArquivo());

        notaFiscalCliente.setXml(xml);
        notaFiscalCliente.setPathXmlServer(pathArquivoXml);
        notaFiscalCliente = repository.save(notaFiscalCliente);
        return notaFiscalDissambler.DomainToModel(notaFiscalCliente);

    }

    public NotaFiscalModel buscar(String id) {
        NotaFiscalCliente nfDomain = repository.findById(id).orElseThrow(() -> new EntidadeNaoEncontradaException(id));
        return notaFiscalDissambler.DomainToModel(nfDomain);
    }

    public void cadastrarProdutos() throws Exception {

        List<NotaFiscalCliente> listaNfPrincipal = notaFsicalRepository.findByStatus(1L);

        if (listaNfPrincipal.size() > 0) {
            listaNfPrincipal.forEach(p -> xmlNotaFiscal.parseNotaFiscal(p));
            List<Produto> produtos = xmlNotaFiscal.geraListaProdutos(listaNfPrincipal);
            cadastroProdutoService.cadastrarProdutos(produtos);

            listaNfPrincipal.forEach(p -> {
                notaFsicalRepository.save(p);
            });
        }

    }
}
