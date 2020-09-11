package br.com.cupom.utils;

import br.com.cupom.api.model.NotaFiscalModel;
import br.com.cupom.utils.notafiscal.TNFe;
import br.com.cupom.utils.notafiscal.TNfeProc;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
@Data
public class LeitorXml {

    private TNfeProc abrirXmlJaxb(InputStream is) throws JAXBException {

        JAXBContext jaxbContext = JAXBContext.newInstance(TNfeProc.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        TNfeProc tNfeProc = (TNfeProc) unmarshaller.unmarshal(is);

        return tNfeProc;


    }

    public void processarArquivoXML(NotaFiscalModel notaFiscalModel) throws IOException, JAXBException {

        TNfeProc tNfeProc = abrirXmlJaxb(notaFiscalModel.getArquivo().getInputStream());

        List<TNFe.InfNFe.Det> det = tNfeProc.getNFe().getInfNFe().getDet();

        System.out.println(tNfeProc.getNFe().getInfNFe().getDet());

    }


}
