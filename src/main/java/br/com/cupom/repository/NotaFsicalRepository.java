package br.com.cupom.repository;

import br.com.cupom.model.NotaFiscalCliente;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotaFsicalRepository extends MongoRepository<NotaFiscalCliente,String> {

}
