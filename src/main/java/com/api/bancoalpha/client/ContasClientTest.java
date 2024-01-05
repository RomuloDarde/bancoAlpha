package com.api.bancoalpha.client;

import com.api.bancoalpha.model.RazaoSocial;
import com.api.bancoalpha.util.ConsumoApi;

public class ContasClientTest {

    public static void main(String[] args) {
        var contasDao = new ContasClientDAO();

        RazaoSocial razaoSocial = new ConsumoApi().salvarDadosContaPeloCnpj("90312133000196");
        System.out.println(razaoSocial.razao_social());


        //System.out.println(contasDao.listarContas());





    }
}
