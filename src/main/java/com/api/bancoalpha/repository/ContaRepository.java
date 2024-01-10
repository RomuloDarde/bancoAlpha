package com.api.bancoalpha.repository;

import com.api.bancoalpha.model.Conta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContaRepository extends JpaRepository<Conta, Long> {
    //Tipo da Entidade que o repository irá representar: Conta
    //Tipo de identificador que será representador: Id > Long
}
