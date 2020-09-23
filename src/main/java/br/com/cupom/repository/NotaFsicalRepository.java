package br.com.cupom.repository;

import br.com.cupom.model.NotaFiscalCliente;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotaFsicalRepository extends MongoRepository<NotaFiscalCliente,String> {


    List<NotaFiscalCliente> findByStatus(Integer status);


}
