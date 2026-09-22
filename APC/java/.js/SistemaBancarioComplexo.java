```java
import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

public class SistemaBancarioComplexo {

    public static void main(String[] args) {

        Banco banco = new Banco("Banco Java Internacional");

        Cliente cliente1 = new Cliente(
                "Breno",
                "123.456.789-00",
                "breno@email.com"
        );

        Cliente cliente2 = new Cliente(
                "Maria",
                "987.654.321-00",
                "maria@email.com"
        );

        ContaCorrente contaBreno =
                banco.criarContaCorrente(
                        cliente1,
                        new BigDecimal("1000.00")
                );

        ContaPoupanca contaMaria =
                banco.criarContaPoupanca(
                        cliente2,
                        new BigDecimal("0.50")
                );

        ExecutorService executor =
                Executors.newFixedThreadPool(4);

        List<Callable<Void>> tarefas = List.of(

                () -> {
                    contaBreno.depositar(
                            new BigDecimal("2500.00")
                    );
                    return null;
                },

                () -> {
                    contaBreno.transferir(
                            contaMaria,
                            new BigDecimal("700.00")
                    );
                    return null;
                },

                () -> {
                    contaMaria.depositar(
                            new BigDecimal("1500.00")
                    );
                    return null;
                },

                () -> {
                    contaMaria.renderJuros();
                    return null;
                }
        );

        try {
            executor.invokeAll(tarefas);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            executor.shutdown();
        }

        banco.exibirRelatorio();
        banco.salvarDados("relatorio_banco.txt");
    }
}

interface OperacoesBancarias {

    void depositar(BigDecimal valor);

    void sacar(BigDecimal valor);

    void transferir(Conta destino, BigDecimal valor);
}

class Banco {

    private final String nome;

    private final Map<Long, Conta> contas =
            new ConcurrentHashMap<>();

    public Banco(String nome) {
        this.nome = nome;
    }

    public ContaCorrente criarContaCorrente(
            Cliente cliente,
            BigDecimal limite
    ) {

        ContaCorrente conta =
                new ContaCorrente(cliente, limite);

        contas.put(conta.getNumero(), conta);

        return conta;
    }

    public ContaPoupanca criarContaPoupanca(
            Cliente cliente,
            BigDecimal taxaJuros
    ) {

        ContaPoupanca conta =
                new ContaPoupanca(cliente, taxaJuros);

        contas.put(conta.getNumero(), conta);

        return conta;
    }

    public void exibirRelatorio() {

        System.out.println(
                "\n===== " +
                nome.toUpperCase() +
                " =====\n"
        );

        contas.values()
                .stream()
                .sorted(
                        Comparator.comparing(
                                Conta::getSaldo
                        ).reversed()
                )
                .forEach(conta -> {

                    System.out.println(conta);

                    conta.getHistorico()
                            .forEach(System.out::println);

                    System.out.println(
                            "-----------------------------"
                    );
                });

        BigDecimal patrimonio =
                contas.values()
                        .stream()
                        .map(Conta::getSaldo)
                        .reduce(
                                BigDecimal.ZERO,
                                BigDecimal::add
                        );

        System.out.println(
                "\nPatrimônio total: R$ " +
                patrimonio
        );
    }

    public void salvarDados(String arquivo) {

        try (
                BufferedWriter writer =
                        new BufferedWriter(
                                new FileWriter(arquivo)
                        )
        ) {

            writer.write(
                    "RELATÓRIO - " + nome
            );

            writer.newLine();
            writer.newLine();

            for (Conta conta : contas.values()) {

                writer.write(
                        conta.toString()
                );

                writer.newLine();

                for (
                        Transacao transacao :
                        conta.getHistorico()
                ) {

                    writer.write(
                            "   " + transacao
                    );

                    writer.newLine();
                }

                writer.newLine();
            }

        } catch (IOException e) {

            System.out.println(
                    "Erro: " + e.getMessage()
            );
        }
    }
}

class Cliente {

    private final String nome;
    private final String cpf;
    private final String email;

    public Cliente(
            String nome,
            String cpf,
            String email
    ) {

        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {

        return nome +
                " | CPF: " +
                cpf +
                " | Email: " +
                email;
    }
}

abstract class Conta
        implements OperacoesBancarias {

    private static final AtomicLong GERADOR =
            new AtomicLong(1000);

    private final long numero;

    protected final Cliente titular;

    protected BigDecimal saldo;

    private final List<Transacao> historico =
            new CopyOnWriteArrayList<>();

    protected Conta(Cliente titular) {

        this.numero =
                GERADOR.incrementAndGet();

        this.titular = titular;

        this.saldo =
                BigDecimal.ZERO;
    }

    @Override
    public synchronized void depositar(
            BigDecimal valor
    ) {

        validar(valor);

        saldo = saldo.add(valor);

        registrar(
                TipoTransacao.DEPOSITO,
                valor,
                "Depósito realizado"
        );
    }

    @Override
    public synchronized void sacar(
            BigDecimal valor
    ) {

        validar(valor);

        if (!podeSacar(valor)) {

            throw new SaldoInsuficienteException(
                    "Saldo insuficiente"
            );
        }

        saldo = saldo.subtract(valor);

        registrar(
                TipoTransacao.SAQUE,
                valor,
                "Saque realizado"
        );
    }

    @Override
    public void transferir(
            Conta destino,
            BigDecimal valor
    ) {

        Objects.requireNonNull(
                destino,
                "Destino inválido"
        );

        validar(valor);

        Conta primeira =
                this.numero < destino.numero
                        ? this
                        : destino;

        Conta segunda =
                this.numero < destino.numero
                        ? destino
                        : this;

        synchronized (primeira) {

            synchronized (segunda) {

                if (!podeSacar(valor)) {

                    throw new SaldoInsuficienteException(
                            "Saldo insuficiente"
                    );
                }

                saldo =
                        saldo.subtract(valor);

                destino.saldo =
                        destino.saldo.add(valor);

                registrar(
                        TipoTransacao
                                .TRANSFERENCIA_ENVIADA,
                        valor,
                        "Transferência enviada"
                );

                destino.registrar(
                        TipoTransacao
                                .TRANSFERENCIA_RECEBIDA,
                        valor,
                        "Transferência recebida"
                );
            }
        }
    }

    protected boolean podeSacar(
            BigDecimal valor
    ) {

        return saldo.compareTo(valor) >= 0;
    }

    private void validar(
            BigDecimal valor
    ) {

        if (
                valor == null ||
                valor.compareTo(
                        BigDecimal.ZERO
                ) <= 0
        ) {

            throw new IllegalArgumentException(
                    "Valor inválido"
            );
        }
    }

    protected void registrar(
            TipoTransacao tipo,
            BigDecimal valor,
            String descricao
    ) {

        historico.add(
                new Transacao(
                        tipo,
                        valor,
                        descricao
                )
        );
    }

    public long getNumero() {
        return numero;
    }

    public BigDecimal getSaldo() {

        return saldo.setScale(
                2,
                RoundingMode.HALF_UP
        );
    }

    public Cliente getTitular() {
        return titular;
    }

    public List<Transacao> getHistorico() {

        return Collections.unmodifiableList(
                historico
        );
    }

    @Override
    public String toString() {

        return getClass().getSimpleName() +
                " | Nº " +
                numero +
                " | Titular: " +
                titular.getNome() +
                " | Saldo: R$ " +
                getSaldo();
    }
}

class ContaCorrente extends Conta {

    private final BigDecimal limite;

    public ContaCorrente(
            Cliente titular,
            BigDecimal limite
    ) {

        super(titular);

        this.limite = limite;
    }

    @Override
    protected boolean podeSacar(
            BigDecimal valor
    ) {

        return saldo
                .add(limite)
                .compareTo(valor) >= 0;
    }

    public BigDecimal getLimite() {
        return limite;
    }
}

class ContaPoupanca extends Conta {

    private final BigDecimal taxaJuros;

    public ContaPoupanca(
            Cliente titular,
            BigDecimal taxaJuros
    ) {

        super(titular);

        this.taxaJuros = taxaJuros;
    }

    public synchronized void renderJuros() {

        BigDecimal juros =
                saldo.multiply(taxaJuros)
                        .divide(
                                new BigDecimal("100"),
                                2,
                                RoundingMode.HALF_UP
                        );

        saldo = saldo.add(juros);

        registrar(
                TipoTransacao.RENDIMENTO,
                juros,
                "Rendimento aplicado"
        );
    }
}

class Transacao {

    private final UUID id;
    private final TipoTransacao tipo;
    private final BigDecimal valor;
    private final String descricao;
    private final LocalDateTime data;

    public Transacao(
            TipoTransacao tipo,
            BigDecimal valor,
            String descricao
    ) {

        this.id =
                UUID.randomUUID();

        this.tipo = tipo;

        this.valor = valor;

        this.descricao = descricao;

        this.data =
                LocalDateTime.now();
    }

    @Override
    public String toString() {

        DateTimeFormatter formato =
                DateTimeFormatter.ofPattern(
                        "dd/MM/yyyy HH:mm:ss"
                );

        return "[" +
                data.format(formato) +
                "] " +
                tipo +
                " | R$ " +
                valor +
                " | " +
                descricao +
                " | ID: " +
                id;
    }
}

enum TipoTransacao {

    DEPOSITO,
    SAQUE,
    TRANSFERENCIA_ENVIADA,
    TRANSFERENCIA_RECEBIDA,
    RENDIMENTO
}

class SaldoInsuficienteException
        extends RuntimeException {

    public SaldoInsuficienteException(
            String mensagem
    ) {

        super(mensagem);
    }
}
```
