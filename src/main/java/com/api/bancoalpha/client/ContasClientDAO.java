package com.api.bancoalpha.client;

import com.api.bancoalpha.exception.ContaInexistenteException;
import com.api.bancoalpha.exception.SaldoInsuficienteException;
import com.api.bancoalpha.exception.ValorIncorretoException;
import com.api.bancoalpha.model.Conta;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

public class ContasClientDAO {
    //Atributos
    private final RestTemplate restTemplate = new RestTemplateBuilder()
            .rootUri("http://localhost:8080/banco_alpha").build();


    //Métodos Públicos
    public List<Conta> listarContas() {
        return Arrays.asList
                (restTemplate.getForObject("/contas", Conta[].class));
    }

    public Conta listarContasPorNumero(Long numeroConta) {
        verificarSeContaExiste(numeroConta);
        return restTemplate.getForObject
                ("/contas/{numero}", Conta.class, numeroConta);
    }

    public void cadastrarConta(Conta contaPost) {
        restTemplate.postForObject("/contas", contaPost, Conta.class);
    }

    public void deletarContaPorNumero (Long numeroConta) {
        verificarSeContaExiste(numeroConta);
        restTemplate.delete("/contas/{numero}", numeroConta);
    }

    public void depositar (Long numeroConta, Double valor) {
        if (valor > 0) {
            var conta = listarContasPorNumero(numeroConta);
            conta.setSaldo(conta.getSaldo() + valor);
            restTemplate.put("/contas", conta);
        } else throw new ValorIncorretoException("Valor da operação deve ser maior que 0.");
    }

    public void pagar (Long numeroConta, Long codigoDeBarras, Double valor) {
        var conta = listarContasPorNumero(numeroConta);
        if (valor > 0) {
            if (conta.getSaldo() >= valor) {
                conta.setSaldo(conta.getSaldo() - valor);
                restTemplate.put("/contas", conta);
            } else throw new SaldoInsuficienteException("Saldo insuficiente.");
        } else throw new ValorIncorretoException("Valor da operação deve ser maior que 0.");

    }

    public void transferir (Long numeroContaOrigem, Long numeroContaDestino, Double valor) {
        pagar(numeroContaOrigem, 1L, valor);
        depositar(numeroContaDestino, valor);
    }

    public String consultarSaldo(Long numeroConta) {
        var conta = listarContasPorNumero(numeroConta);
        return String.format("Saldo da conta " + conta.getNumero() + ": R$ %.2f", conta.getSaldo());
    }

    public void atualizarDados(Long numeroConta, String cnpj, String titular, String email, String endereco, String senha) {
        var conta = listarContasPorNumero(numeroConta);
        conta.setCnpj(cnpj);
        conta.setRazaoSocial(titular);
        conta.setEmail(email);
        conta.setEndereco(endereco);
        conta.setSenha(senha);
        restTemplate.put("/contas", conta);
    }



    //Métodos Privados
    private void verificarSeContaExiste(Long numeroConta) {
        if (restTemplate.getForObject("/contas/{numero}",
                Conta.class, numeroConta) == null ) {
            throw new ContaInexistenteException("Conta inexistente.");
        }
    }




}
