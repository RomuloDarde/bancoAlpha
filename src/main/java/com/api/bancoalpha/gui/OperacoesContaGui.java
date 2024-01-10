package com.api.bancoalpha.gui;

import com.api.bancoalpha.client.ContasClientDAO;
import com.api.bancoalpha.exception.SaldoInsuficienteException;
import com.api.bancoalpha.exception.ValorIncorretoException;
import com.api.bancoalpha.model.Conta;
import org.springframework.web.client.HttpClientErrorException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OperacoesContaGui extends JFrame{

    //Atributos
    private JLabel labelTitulo;
    private JLabel labelOperacoes;
    private JButton buttonConsultarSaldo;
    private JButton buttonTransferir;
    private JTextField textValorTransf;
    private JLabel labelAvisoSaldo;
    private JLabel labelAvisoTransf;
    private JButton buttonDepositar;
    private JTextField textContaTransf;
    private JTextField textValorDep;
    private JLabel labelAvisoDep;
    private JButton buttonPagar;
    private JTextField textCodBarrasPag;
    private JTextField textValorPag;
    private JLabel labelAvisoPag;
    private JLabel labelValorDep;
    private JLabel labelContaDestTransf;
    private JLabel labelValorTransf;
    private JLabel labelCodBarras;
    private JLabel labelValorPag;
    private JLabel labelDigiteAConta;
    private JTextField textFieldNumeroConta;
    private JTextField textFieldSenha;
    private JLabel labelSenha;
    private JButton buttonLogIn;
    private JLabel labelAvisoLogIn;
    private JPanel mainPanel;
    private JLabel labelBemVindo;
    private JButton buttonLogOut;
    private JLabel labelAvisoLogOut;
    private boolean logInAtivo;
    private Long numeroContaOp;



    public OperacoesContaGui() {
        //Atributos
        ContasClientDAO clientDAO = new ContasClientDAO();

        //Configurações
        setContentPane(mainPanel);
        setTitle("Banco Alpha - Login / Operações");   // Título da janela
        setDefaultCloseOperation(EXIT_ON_CLOSE);   // Finaliza o projeto quando fechar a janela
        setSize(800, 650);   // Tamanho inicial
        setLocationRelativeTo(null);
        setVisible(true);

        //Botão Login
        buttonLogIn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Long numeroConta = Long.parseLong(textFieldNumeroConta.getText());
                    String senha = textFieldSenha.getText();
                    Conta conta = clientDAO.listarContasPorNumero(numeroConta);

                    if (!conta.getSenha().equals(senha)) {
                        labelAvisoLogIn.setText("Senha Incorreta.");
                    } else {
                        logInAtivo = true;
                        numeroContaOp = numeroConta;
                        limparTodosCampos();
                        mensagemLoginAtivo(conta);
                        textFieldNumeroConta.setText(Long.toString(numeroConta));
                    }

                } catch (HttpClientErrorException exception) {
                    labelAvisoLogIn.setText("Número de Conta Inválido.");
                } catch (NumberFormatException exception) {
                    labelAvisoLogIn.setText("Número de Conta Inválido.");
                }
            }
        });


        //Botão Log Out
        buttonLogOut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logInAtivo = false;
                limparTodosCampos();
                labelAvisoLogOut.setText("Logout efetuado com sucesso.");
                Long numeroConta = 0L;
                String senha = ("");
            }
        });


        //Botão Consultar Saldo
        buttonConsultarSaldo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limparCamposAviso();
                limparCampoDeposito();
                limparCamposPagamento();
                limparCamposTransferencia();

                if (logInAtivo) {
                    labelAvisoSaldo.setText(clientDAO.consultarSaldo(numeroContaOp));
                } else labelAvisoSaldo.setText("Login não efetuado.");
            }
        });

        //Botão Depositar
        buttonDepositar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limparCamposAviso();
                limparCamposPagamento();
                limparCamposTransferencia();

                if (logInAtivo) {
                    try {
                        Double valor = Double.parseDouble(textValorDep.getText()
                                .replace(',', '.'));
                        clientDAO.depositar(numeroContaOp, valor);
                        labelAvisoDep.setText("Depósito efetuado com sucesso");

                    } catch (ValorIncorretoException exception) {
                        labelAvisoDep.setText("Valor deve ser maior que 0.");
                    } catch (NumberFormatException exception) {
                        labelAvisoDep.setText("Valor não deve conter caracteres.");
                    }
                } else labelAvisoDep.setText("Login não efetuado.");
            }
        });

        //Botão Pagar
        buttonPagar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limparCamposAviso();
                limparCampoDeposito();
                limparCamposTransferencia();

                if (logInAtivo) {
                    try {
                        Double valor = Double.parseDouble(textValorPag.getText()
                                .replace(',', '.'));

                        try {
                            Long codigoDeBarras = Long.parseLong(textCodBarrasPag.getText());
                            clientDAO.pagar(numeroContaOp, codigoDeBarras, valor);
                            labelAvisoPag.setText("Pagamento efetuado com sucesso");

                        } catch (NumberFormatException exception) {
                            labelAvisoPag.setText("Código de barras incorreto (somente números).");
                        }

                    } catch (ValorIncorretoException exception) {
                        labelAvisoPag.setText("Valor deve ser maior que 0.");
                    } catch (SaldoInsuficienteException exception) {
                        labelAvisoPag.setText("Saldo insuficiente.");
                    } catch (NumberFormatException exception) {
                    labelAvisoPag.setText("Valor não deve conter caracteres.");
                    }

                } else labelAvisoPag.setText("Login não efetuado.");
            }
        });

        //Botão Transferir
        buttonTransferir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limparCamposAviso();
                limparCampoDeposito();
                limparCamposPagamento();

                if (logInAtivo) {
                    try {
                        Double valor = Double.parseDouble(textValorTransf.getText()
                                .replace(',', '.'));
                        Long numeroContaDestino = Long.parseLong(textContaTransf.getText());

                        clientDAO.transferir(numeroContaOp, numeroContaDestino, valor);
                        labelAvisoTransf.setText("Transferido para a conta de: " +
                                clientDAO.listarContasPorNumero(numeroContaDestino).getRazaoSocial() + ".");

                    } catch (HttpClientErrorException exception) {
                        labelAvisoTransf.setText("Número da conta destino inválido.");
                    }  catch (ValorIncorretoException exception) {
                        labelAvisoTransf.setText("Valor deve ser maior que 0.");
                    } catch (SaldoInsuficienteException exception) {
                        labelAvisoTransf.setText("Saldo insuficiente.");
                    } catch (NumberFormatException exception) {
                    labelAvisoTransf.setText("Valor não deve conter caracteres.");
                    }

                } else labelAvisoTransf.setText("Login não efetuado.");
            }
        });
    }

    private void limparTodosCampos() {
        textFieldNumeroConta.setText("");
        textFieldSenha.setText("");
        textValorDep.setText("");
        textContaTransf.setText("");
        textValorTransf.setText("");
        textCodBarrasPag.setText("");
        textValorPag.setText("");
        labelAvisoLogOut.setText("");
        labelBemVindo.setText("");
        labelAvisoLogIn.setText("");
        labelAvisoDep.setText("");
        labelAvisoPag.setText("");
        labelAvisoTransf.setText("");
        labelAvisoSaldo.setText("");
    }

    private void limparCamposAviso() {
        labelAvisoDep.setText("");
        labelAvisoPag.setText("");
        labelAvisoTransf.setText("");
        labelAvisoSaldo.setText("");
    }

    private void limparCampoDeposito() {
        textValorDep.setText("");
    }

    private void limparCamposPagamento() {
        textCodBarrasPag.setText("");
        textValorPag.setText("");
    }

    private void limparCamposTransferencia() {
        textContaTransf.setText("");
        textValorTransf.setText("");
    }
    public void mensagemLoginAtivo (Conta conta) {
        labelAvisoLogIn.setText("Login efetuado com sucesso!.");
        labelBemVindo.setText("Bem vindo, " + conta.getRazaoSocial() + "!");
    }

    public static void main(String[] args) {
        new OperacoesContaGui();
    }
}
