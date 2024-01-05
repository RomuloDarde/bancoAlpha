package com.api.bancoalpha.controller;

import com.api.bancoalpha.exception.ContaInexistenteException;
import com.api.bancoalpha.model.Conta;
import com.api.bancoalpha.repository.ContaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("banco_alpha/contas")
public class ContaController {

    //Atributos
    @Autowired
    private ContaRepository repository;


    //Metodos
    @GetMapping
    public List<Conta> getAll() {
        return repository.findAll();
    }

    @GetMapping (path = "{numero}")
    public Conta getById(@PathVariable("numero") Long numeroConta) {
        verifyIfContaExists(numeroConta);
        return repository.findById(numeroConta).get();
    }

    @PostMapping
    public void save(@RequestBody Conta conta) {
        repository.save(new Conta(conta));
    }

    @DeleteMapping (path = "{numero}")
    public void deleteById(@PathVariable("numero") Long numeroConta) {
        verifyIfContaExists(numeroConta);
        repository.deleteById(numeroConta);
    }

    @PutMapping
    public void update (@RequestBody Conta conta) {
        verifyIfContaExists(conta.getNumero());
        repository.save(conta);
    }

    private void verifyIfContaExists(Long numeroConta) {
        if (repository.findById(numeroConta).isEmpty()) {
            throw new ContaInexistenteException("Conta inexistente.");
        }
    }


}
