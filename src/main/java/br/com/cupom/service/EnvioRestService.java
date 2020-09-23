package br.com.cupom.service;

import br.com.cupom.api.exception.Problem;
import br.com.cupom.api.model.Produto;
import br.com.cupom.service.rest.StatusServiceRest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Service
public class EnvioRestService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${url.cupom.cadastro.produto}")
    private String urlProduto;

    @Autowired
    private ObjectMapper objectMapper;

    private StatusServiceRest restException(Exception ex, HttpMethod method, HttpStatus status, Object object) {

        StatusServiceRest erroServiceRest = StatusServiceRest.builder()
                .url(urlProduto)
                .method(method.toString())
                .status(status)
                .title("Erro ao enviar")
                .message(ex.getCause().getMessage())
                .classe(object.getClass().getName())
                .build();

        return erroServiceRest;

    }

    private StatusServiceRest restClientException(RestClientException ex, HttpMethod method, HttpStatus status, Object object) {

        StatusServiceRest erroServiceRest = StatusServiceRest.builder()
                .url(urlProduto)
                .method(method.toString())
                .status(status)
                .title("Erro ao enviar")
                .message(ex.getCause().getMessage())
                .classe(object.getClass().getName())
                .build();

        return erroServiceRest;

    }

    private StatusServiceRest httpClientErrorException(HttpClientErrorException ex, HttpMethod method, Object object) {

        Problem problem;

        try {
            problem = objectMapper.readValue(ex.getResponseBodyAsString(), Problem.class);
        } catch (JsonProcessingException e) {
            problem = Problem.builder()
                    .status(ex.getStatusCode().value())
                    .title("Responsa do servidor")
                    .userMessage(ex.getResponseBodyAsString())
                    .build();
        }

        StatusServiceRest erroServiceRest = StatusServiceRest.builder()
                .url(urlProduto)
                .method(method.toString())
                .status(ex.getStatusCode())
                .title("Erro ao enviar")
                .message(problem.getUserMessage())
                .classe(object.getClass().getName())
                .build();

        return erroServiceRest;

    }

    private StatusServiceRest httpOk(ResponseEntity<String> entity, Object object) {

        StatusServiceRest statusServiceRest = StatusServiceRest.builder()
                .url(urlProduto)
                .status(entity.getStatusCode())
                .title("Enviado com sucesso")
                .message(entity.getStatusCode().getReasonPhrase())
                .classe(object.getClass().getName())
                .build();

        return statusServiceRest;

    }

    private ResponseEntity<String> chamadaRest(Object corpoPost, HttpMethod httpMethod) {

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<Object> httpEntity = new HttpEntity<>(corpoPost, headers);
        ResponseEntity<String> entity = restTemplate.postForEntity(urlProduto, httpEntity, String.class);

        return entity;

    }

    private StatusServiceRest enviar(Object object, HttpMethod method) {

        try {
            ResponseEntity<String> entity = chamadaRest(object, method);
            return httpOk(entity, object);
        } catch (HttpClientErrorException ex){
            return httpClientErrorException(ex, method, object);
        } catch (RestClientException ex) {
            return restClientException(ex, method, HttpStatus.INTERNAL_SERVER_ERROR, object);
        } catch (Exception ex) {
            return restException(ex, method, HttpStatus.INTERNAL_SERVER_ERROR, object);
        }

    }

    public Boolean produto(Produto produto) {

        System.out.println(produto);

        StatusServiceRest statusServiceRest = enviar(produto, HttpMethod.POST);

        if (statusServiceRest.getStatus().isError() ) {
            produto.setMessage(statusServiceRest.toString());
            return Boolean.FALSE;
        }
        return Boolean.TRUE;

    }

}
