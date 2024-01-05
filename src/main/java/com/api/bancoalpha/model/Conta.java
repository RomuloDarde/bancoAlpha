package com.api.bancoalpha.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Entity (name = "contas")
@Table(name = "contas")
public class Conta {

    //Atributos
    @Id
    private long numero;

    @NotEmpty
    @NotNull
    private String cnpj;
    @NotNull
    @NotEmpty
    private String razaoSocial;
    @NotNull
    @NotEmpty
    private String email;
    @NotNull
    @NotEmpty
    @Column(length = 500)
    private String endereco;
    @NotNull
    @NotEmpty
    private String senha;
    private Double saldo;
    @NotNull
    @NotEmpty
    private String dataAbertura;


    //Construtores
    public Conta() {}

    public Conta(Conta conta) {
        this.cnpj = conta.getCnpj();
        this.razaoSocial = conta.getRazaoSocial();
        this.email = conta.getEmail();
        this.endereco = conta.getEndereco();
        this.senha = conta.getSenha();
        this.saldo = 0.0;
        this.numero = (long) (100000 + Math.random() * 1000000);
        this.dataAbertura = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public Conta(String cnpj, String razaoSocial, String email, String endereco, String senha) {
        this.numero = (long) (100000 + Math.random() * 1000000);
        this.cnpj = cnpj;
        this.razaoSocial = razaoSocial;
        this.email = email;
        this.endereco = endereco;
        this.senha = senha;
        this.saldo = 0.0;
        this.dataAbertura = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    //Getters
    public String getCnpj() {return cnpj;}
    public String getRazaoSocial() {return razaoSocial;}
    public String getEmail() {return email;}
    public String getEndereco() {return endereco;}
    public Double getSaldo() {return saldo;}
    public long getNumero() {return numero;}
    public String getSenha() {return senha;}
    public String getDataAbertura() {return dataAbertura;}

    //Setters
    public void setSaldo(Double saldo) {this.saldo = saldo;}
    public void setCnpj(String cnpj) {this.cnpj = cnpj;}
    public void setRazaoSocial(String razaoSocial) {this.razaoSocial = razaoSocial;}
    public void setEmail(String email) {this.email = email;}
    public void setEndereco(String endereco) {this.endereco = endereco;}
    public void setSenha(String senha) {this.senha = senha;}
    public void setDataAbertura(String dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    //Equals e HashCode do Número
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Conta conta = (Conta) o;
        return numero == conta.numero;
    }

    @Override
    public int hashCode() {
        return Objects.hash(numero);
    }

    //To String
    @Override
    public String toString() {
        return "Número: " + numero + " - CNPJ: " + cnpj + " - Titular: " + razaoSocial +
                " - Saldo: R$ " + saldo + " - E-mail: " + email + " - Endereco: " + endereco +
                " - Data de abertura: " + dataAbertura + "\n";
    }
}

