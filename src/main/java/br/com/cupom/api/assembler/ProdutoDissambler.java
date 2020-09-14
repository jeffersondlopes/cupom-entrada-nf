package br.com.cupom.api.assembler;

import br.com.cupom.api.model.Produto;
import br.com.cupom.utils.notafiscal.TNFe;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProdutoDissambler {

    @Autowired
    private ModelMapper modelMapper;

    public Produto map(TNFe.InfNFe.Det.Prod produto){
        return modelMapper.map(produto, Produto.class);
    }


}
