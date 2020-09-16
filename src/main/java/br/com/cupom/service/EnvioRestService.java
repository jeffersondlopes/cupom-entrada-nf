package br.com.cupom.service;

import br.com.cupom.api.model.Produto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class EnvioRestService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${url.cupom.cadastro.produto}")
    private String urlProduto;

    private void restClientException(RestClientException ex, HttpMethod post){

        System.out.println(ex.getMessage());
        System.out.println(ex.getCause().getMessage());

    }

    private ResponseEntity<String> chamadaRest(Object corpoPost, HttpMethod httpMethod) {

        HttpEntity<Object> httpEntity = new HttpEntity<>(corpoPost);

        ResponseEntity<String> responseEntity = restTemplate.exchange(urlProduto, httpMethod, httpEntity, String.class);

        return responseEntity;

    }

    private void enviar(Object object) {

        try {
            chamadaRest(object, HttpMethod.POST);
        } catch (RestClientException ex) {
            restClientException(ex,HttpMethod.POST);
        } catch (Exception ex) {
            System.out.println("outra exception");
        }

    }

    public void produto (Produto produto){
        enviar(produto);
    }

}
