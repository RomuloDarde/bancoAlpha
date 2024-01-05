package com.api.bancoalpha.util;

import com.api.bancoalpha.model.Endereco;
import com.api.bancoalpha.model.RazaoSocial;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsumoApi {
    //Atributos
    private HttpClient client;
    private HttpRequest request;
    private HttpResponse<String> response;
    private String uri;


    //Construtor
    public ConsumoApi()  {
        client = HttpClient.newHttpClient();
    }

    //Métodos
    public Endereco salvaEnderecoPeloCep(String cep) {
        uri = "https://viacep.com.br/ws/" + cep + "/json/";
        request = HttpRequest.newBuilder()
                .uri(URI.create(this.uri)).build();
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return new Gson().fromJson(response.body(), Endereco.class);
    }

    public RazaoSocial salvarDadosContaPeloCnpj (String cnpj) {
        uri = "https://publica.cnpj.ws/cnpj/" + cnpj;
        request = HttpRequest.newBuilder()
                .uri(URI.create(this.uri)).build();
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return new Gson().fromJson(response.body(), RazaoSocial.class);
    }
}
