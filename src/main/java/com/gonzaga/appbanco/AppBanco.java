/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.gonzaga.appbanco;

import java.util.*;

public class AppBanco {

    private List<Cliente> clientes;

    public static void main(String[] args) {
        AppBanco appBanco = new AppBanco();
        appBanco.executar();
    }

    public AppBanco() {
        clientes = new ArrayList<>();
    }

    public void executar() {
        Scanner input = new Scanner(System.in);
        Cliente cliente = null;
        int option;

        do {
            option = showAndGetMenuOption();

            switch (option) {
                case 1:
                    cliente = criarConta(input);
                    if (cliente != null) {
                        System.out.println("Conta criada com sucesso!");
                        System.out.println("ID da Conta: " + cliente.getId());
                        System.out.println("Cliente: " + cliente.getNome() + " " + cliente.getSobrenome());
                    }
                    break;

                case 2:
                    realizarDeposito(input, cliente);
                    break;

                case 3:
                    realizarSaque(input, cliente);
                    break;

                case 4:
                    consultarSaldo(cliente);
                    break;

                case 5:
                    System.out.println("Encerrando a aplicação...");
                    return;

                case 375:
                    listarClientes();
                    break;

                default:
                    System.out.println("Opção Inválida: Insira um número de 1 a 5");
                    break;
            }

        } while (option != 5);
    }

    private int showAndGetMenuOption() {
        System.out.println("""
                ----------------------------------------------------------------
                BANK SYSTEM MENU
                ----------------------------------------------------------------
                Selecione uma Opção:
                1 - Criar Conta Bancária
                2 - Depositar
                3 - Sacar
                4 - Saldo
                5 - Sair
                ----------------------------------------------------------------
                """);

        try {
            return new Scanner(System.in).nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Por favor, insira um número válido.");
            return 0;
        }
    }

    private Cliente criarConta(Scanner input) {
        System.out.println("Digite seu primeiro nome: ");
        String nome = input.nextLine();

        System.out.println("Digite seu sobrenome: ");
        String sobrenome = input.nextLine();

        System.out.println("Digite seu CPF: ");
        String cpf = input.nextLine();

        int id = gerarId();
        Cliente cliente = new Cliente(id, nome, sobrenome, cpf);
        clientes.add(cliente);
        return cliente;
    }

    private void realizarDeposito(Scanner input, Cliente cliente) {
        if (cliente != null) {
            System.out.println("Por favor, digite o ID da conta na qual deseja realizar o depósito: ");
            int idCliente = input.nextInt();
            cliente = buscarClientePorId(idCliente);

            System.out.println("Digite o valor que deseja depositar: ");
            double valorDeposito = input.nextDouble();

            if (valorDeposito > 0) {
                cliente.depositar(valorDeposito);
                System.out.println("Depósito de " + valorDeposito + " realizado com sucesso.");
            } else {
                System.out.println("Valor de depósito inválido. O valor deve ser positivo.");
            }
        } else {
            System.out.println("Por favor, crie uma conta primeiro!");
        }
    }

    private void realizarSaque(Scanner input, Cliente cliente) {
        if (cliente != null) {
            System.out.println("Digite o ID da conta: ");
            int idCliente = input.nextInt();
            cliente = buscarClientePorId(idCliente);

            if (cliente != null) {
                System.out.println("Digite o valor a sacar: ");
                double valorSaque = input.nextDouble();

                if (valorSaque > 0) {
                    cliente.sacar(valorSaque);
                } else {
                    System.out.println("Valor de saque inválido. O valor deve ser positivo.");
                }
            } else {
                System.out.println("Cliente não encontrado. Verifique o ID da conta.");
            }
        } else {
            System.out.println("Por favor, crie uma conta primeiro!");
        }
    }

    private void consultarSaldo(Cliente cliente) {
        if (cliente != null) {
            System.out.println("Digite o ID da conta: ");
            int idCliente = new Scanner(System.in).nextInt();
            cliente = buscarClientePorId(idCliente);

            if (cliente != null) {
                System.out.println("Saldo da conta: " + cliente.getSaldo());
            } else {
                System.out.println("Cliente não encontrado. Verifique o ID da conta.");
            }
        } else {
            System.out.println("Por favor, crie uma conta primeiro!");
        }
    }

    private void listarClientes() {
        System.out.println("Lista de Clientes:");
        for (Cliente cliente : clientes) {
            System.out.println("ID: " + cliente.getId());
            System.out.println("Nome: " + cliente.getNome());
            System.out.println("Sobrenome: " + cliente.getSobrenome());
            System.out.println("CPF: " + cliente.getCpf());
            System.out.println("Saldo: " + cliente.getSaldo());
            System.out.println();
        }
    }

    private Cliente buscarClientePorId(int id) {
        for (Cliente cliente : clientes) {
            if (cliente.getId() == id) {
                return cliente;
            }
        }
        System.out.println("Cliente com ID " + id + " não encontrado.");
        return null;
    }

    private static int gerarId() {
        Random random = new Random();
        return random.nextInt(1, 10000); // Altere o limite conforme necessário
    }

    public static class Cliente {
        private int id;
        private String nome;
        private String sobrenome;
        private String cpf;
        private double saldo;

        public Cliente(int id, String nome, String sobrenome, String cpf) {
            this.id = id;
            this.nome = nome;
            this.sobrenome = sobrenome;
            this.cpf = cpf;
            this.saldo = 0.0;
        }

        public void depositar(double valor) {
            saldo += valor;
        }

        public void sacar(double valor) {
            if (valor > 0 && valor <= saldo) {
                saldo -= valor;
                System.out.println("Saque de " + valor + " realizado com sucesso.");
            } else {
                System.out.println("Saldo insuficiente para realizar o saque.");
            }
        }

        public double getSaldo() {
            return saldo;
        }

        public int getId() {
            return id;
        }

        public String getNome() {
            return nome;
        }

        public String getSobrenome() {
            return sobrenome;
        }

        public String getCpf() {
            return cpf;
        }
    }
}
