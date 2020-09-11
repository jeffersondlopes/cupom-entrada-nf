package br.com.cupom.api.assembler;

import br.com.cupom.api.model.NotaFiscalModel;
import br.com.cupom.model.NotaFiscalCliente;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NotaFiscalDissambler {

    @Autowired
    private ModelMapper modelMapper;

    public NotaFiscalCliente ModelToDomain(NotaFiscalModel notaFiscalModel) {
        return modelMapper.map(notaFiscalModel, NotaFiscalCliente.class);
    }

    public NotaFiscalModel DomainToModel(NotaFiscalCliente notaFiscalCliente) {
        return modelMapper.map(notaFiscalCliente, NotaFiscalModel.class);
    }


}
