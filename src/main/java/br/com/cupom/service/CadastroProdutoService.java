package br.com.cupom.service;

import br.com.cupom.api.model.Produto;
import br.com.cupom.model.NotaFiscalCliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class CadastroProdutoService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${url.cupom.cadastro.produto}")
    private String urlProduto;

    @Autowired
    private EnvioRestService restService;

    public void cadastrarProdutos(List<Produto> produtos){

        produtos.forEach(produto -> {
                restService.produto(produto);
        });

    }

    public void cadastrarProdutos(NotaFiscalCliente nfe) {

        nfe.getProdutoList().forEach(produto -> {
            if (!restService.produto(produto)) {
                nfe.getMensagensErro().add(produto.getMessage());
                nfe.setStatus(99);
            }
        });

    }
}
