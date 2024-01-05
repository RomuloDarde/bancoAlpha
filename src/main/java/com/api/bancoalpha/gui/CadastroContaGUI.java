package com.api.bancoalpha.gui;

import com.api.bancoalpha.client.ContasClientDAO;
import com.api.bancoalpha.model.Conta;
import com.api.bancoalpha.model.Endereco;
import com.api.bancoalpha.model.RazaoSocial;
import com.api.bancoalpha.util.ConsumoApi;
import com.google.gson.JsonSyntaxException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CadastroContaGUI extends JFrame{

    //Atributos
    private JPanel mainPanel;
    private JLabel labelTitulo;
    private JLabel labelCadastro;
    private JTextField textCnpj;
    private JLabel labelCnpj;
    private JTextField textRazaoSocial;
    private JTextField textEmail;
    private JLabel labelRazaoSocial;
    private JLabel labelEmail;
    private JLabel labelEndereco;
    private JButton buttonEnviarDados;
    private JLabel labelRevise;
    private JLabel labelSenha;
    private JTextField textSenha;
    private JLabel labelMensagem;
    private JLabel labelAviso;
    private JLabel labelLogradouro;
    private JLabel labelCep;
    private JTextField textCep;
    private JTextField textLogradouro;
    private JTextField textNumeroEndereco;
    private JTextField textComplementoEndereco;
    private JTextField textBairro;
    private JTextField textCidade;
    private JTextField textEstado;
    private JButton buttonConsultaCep;
    private JButton buttonConsultaCnpj;

    public CadastroContaGUI() {
        //Configurações
        setContentPane(mainPanel);
        setTitle("Banco Alpha - Cadastro");   // Título da janela
        setDefaultCloseOperation(EXIT_ON_CLOSE);   // Finaliza o projeto quando fechar a janela
        setSize(800, 650);   // Tamanho inicial
        setVisible(true);


        //Botão Cadastrar
        buttonEnviarDados.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!textCnpj.getText().isEmpty() && !textRazaoSocial.getText().isEmpty() &&
                !textEmail.getText().isEmpty() && !textLogradouro.getText().isEmpty() &&
                !textNumeroEndereco.getText().isEmpty() && !textBairro.getText().isEmpty() &&
                !textCidade.getText().isEmpty() && !textEstado.getText().isEmpty() &&
                !textSenha.getText().isEmpty()) {

                    String cnpj = textCnpj.getText();
                    String titular = textRazaoSocial.getText();
                    String email = textEmail.getText();
                    String senha = textSenha.getText();

                    String endereco;
                    if (textComplementoEndereco.getText().isEmpty()) {
                        endereco = textLogradouro.getText() + ", " + textNumeroEndereco.getText() + ". " +
                        textBairro.getText() + ", " + textCidade.getText() + " - " +
                                textEstado.getText() + ". CEP: " + textCep.getText() + ".";
                    } else {
                        endereco = textLogradouro.getText() + ", " + textNumeroEndereco.getText() + ", " +
                                textComplementoEndereco.getText() +". " + textBairro.getText() + ", " +
                                textCidade.getText() + " - " + textEstado.getText() +
                                ". CEP: " + textCep.getText() + ".";
                    }

                    new ContasClientDAO().cadastrarConta(new Conta(cnpj, titular, email, endereco, senha));
                    labelMensagem.setText("Dados enviados com sucesso!");
                    labelAviso.setText("Após a análise, você receberá a confirmação da abertura " +
                            "de sua conta por e-mail.");
                } else {
                    labelMensagem.setText("Algum campo está vazio, você deve revisar!");
                }
            }
        });

        //Botão Consultar Cep
        buttonConsultaCep.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cep = textCep.getText();
                try {
                    Endereco endereco = new ConsumoApi().salvaEnderecoPeloCep(cep);

                    textCep.setText(endereco.cep());
                    textLogradouro.setText(endereco.logradouro());
                    textBairro.setText(endereco.bairro());
                    textCidade.setText(endereco.localidade());
                    textEstado.setText(endereco.uf());

                } catch (JsonSyntaxException exception) {
                    labelMensagem.setText("CEP incorreto!");
                    textLogradouro.setText("");
                    textBairro.setText("");
                    textCidade.setText("");
                    textEstado.setText("");
                }

            }
        });


        //Botão Consultar Cnpj
        buttonConsultaCnpj.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cnpj = textCnpj.getText();

                try {
                    RazaoSocial razaoSocial = new ConsumoApi().salvarDadosContaPeloCnpj(cnpj);
                    if (razaoSocial.razao_social() == null) {
                        textRazaoSocial.setText("");
                        labelMensagem.setText("CNPJ incorreto, ou você deve aguardar um minuto para nova consulta.");
                    } else {
                        textRazaoSocial.setText(razaoSocial.razao_social());
                        labelMensagem.setText("");
                    }
                } catch (JsonSyntaxException exception) {
                    textRazaoSocial.setText("");
                    labelMensagem.setText("CNPJ incorreto!");
                }

            }
        });
    }

    public static void main(String[] args) {
        new CadastroContaGUI();
        System.out.println();
    }

}
