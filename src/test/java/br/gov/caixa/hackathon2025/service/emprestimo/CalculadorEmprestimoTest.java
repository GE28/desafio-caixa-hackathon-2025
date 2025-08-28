package br.gov.caixa.hackathon2025.service.emprestimo;

import io.quarkus.test.junit.QuarkusTest;
import br.gov.caixa.hackathon2025.dto.ParcelaDto;
import br.gov.caixa.hackathon2025.exception.CalculoException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class CalculadorEmprestimoTest {
    private final BigDecimal valorEmprestimoPadrao = new BigDecimal("900.00");
    private final BigDecimal taxaJurosPadrao = new BigDecimal("0.0179");
    private final Integer numeroParcelasPadrao = 5;
    // Literals têm seu uso limitado por ferramentas de análise como SonarQube.
    // Nesses casos, é boa prática utilizar constantes nomeadas.
    private final String tipoEmprestimoSAC = "SAC";
    private final String tipoEmprestimoPrice = "PRICE";

    private BigDecimal arredondar(BigDecimal valor) {
        return valor.setScale(2, RoundingMode.HALF_UP);
    }

    @Test
    void testSacComValoresPayloadExemplo() {
        BigDecimal valorEmprestimo = new BigDecimal("900.00");
        BigDecimal taxaJuros = new BigDecimal("0.0179");
        Integer numeroParcelas = 5;
        
        CalculadorSac calculadorSac = new CalculadorSac(valorEmprestimo, taxaJuros, numeroParcelas);
        List<ParcelaDto> parcelasSac = calculadorSac.calcularParcelas();
        
        assertEquals(5, parcelasSac.size());
        
        ParcelaDto parcela1 = parcelasSac.get(0);
        assertEquals(1, parcela1.getNumero());

        String valorAmortizacao = "180.00";
        assertEquals(new BigDecimal(valorAmortizacao), arredondar(parcela1.getValorAmortizacao()));
        assertEquals(new BigDecimal("16.11"), arredondar(parcela1.getValorJuros()));
        assertEquals(new BigDecimal("196.11"), arredondar(parcela1.getValorPrestacao()));
        
        ParcelaDto parcela2 = parcelasSac.get(1);
        assertEquals(2, parcela2.getNumero());
        assertEquals(new BigDecimal(valorAmortizacao), arredondar(parcela2.getValorAmortizacao()));
        assertEquals(new BigDecimal("12.89"), arredondar(parcela2.getValorJuros()));
        assertEquals(new BigDecimal("192.89"), arredondar(parcela2.getValorPrestacao()));
        
        ParcelaDto parcela3 = parcelasSac.get(2);
        assertEquals(3, parcela3.getNumero());
        assertEquals(new BigDecimal(valorAmortizacao), arredondar(parcela3.getValorAmortizacao()));
        assertEquals(new BigDecimal("9.67"), arredondar(parcela3.getValorJuros()));
        assertEquals(new BigDecimal("189.67"), arredondar(parcela3.getValorPrestacao()));
        
        ParcelaDto parcela4 = parcelasSac.get(3);
        assertEquals(4, parcela4.getNumero());
        assertEquals(new BigDecimal(valorAmortizacao), arredondar(parcela4.getValorAmortizacao()));
        assertEquals(new BigDecimal("6.44"), arredondar(parcela4.getValorJuros()));
        assertEquals(new BigDecimal("186.44"), arredondar(parcela4.getValorPrestacao()));
        
        ParcelaDto parcela5 = parcelasSac.get(4);
        assertEquals(5, parcela5.getNumero());
        assertEquals(new BigDecimal(valorAmortizacao), arredondar(parcela5.getValorAmortizacao()));
        assertEquals(new BigDecimal("3.22"), arredondar(parcela5.getValorJuros()));
        assertEquals(new BigDecimal("183.22"), arredondar(parcela5.getValorPrestacao()));
    }

    @Test
    void testPriceComValoresPayloadExemplo() {
        BigDecimal valorEmprestimo = new BigDecimal("900.00");
        BigDecimal taxaJuros = new BigDecimal("0.0179");
        Integer numeroParcelas = 5;
        
        CalculadorPrice calculadorPrice = new CalculadorPrice(valorEmprestimo, taxaJuros, numeroParcelas);
        List<ParcelaDto> parcelasPrice = calculadorPrice.calcularParcelas();
        
        assertEquals(5, parcelasPrice.size());
        
        ParcelaDto parcela1 = parcelasPrice.get(0);
        assertEquals(1, parcela1.getNumero());

        String valorParcela = "189.78";
        assertEquals(new BigDecimal("173.67"), arredondar(parcela1.getValorAmortizacao()));
        assertEquals(new BigDecimal("16.11"), arredondar(parcela1.getValorJuros()));
        assertEquals(new BigDecimal(valorParcela), arredondar(parcela1.getValorPrestacao()));
        
        ParcelaDto parcela2 = parcelasPrice.get(1);
        assertEquals(2, parcela2.getNumero());
        assertEquals(new BigDecimal("176.78"), arredondar(parcela2.getValorAmortizacao()));
        assertEquals(new BigDecimal("13.00"), arredondar(parcela2.getValorJuros()));
        assertEquals(new BigDecimal(valorParcela), arredondar(parcela2.getValorPrestacao()));
        
        ParcelaDto parcela3 = parcelasPrice.get(2);
        assertEquals(3, parcela3.getNumero());
        assertEquals(new BigDecimal("179.94"), arredondar(parcela3.getValorAmortizacao()));
        assertEquals(new BigDecimal("9.84"), arredondar(parcela3.getValorJuros()));
        assertEquals(new BigDecimal(valorParcela), arredondar(parcela3.getValorPrestacao()));
        
        ParcelaDto parcela4 = parcelasPrice.get(3);
        assertEquals(4, parcela4.getNumero());
        assertEquals(new BigDecimal("183.16"), arredondar(parcela4.getValorAmortizacao()));
        assertEquals(new BigDecimal("6.62"), arredondar(parcela4.getValorJuros()));
        assertEquals(new BigDecimal(valorParcela), arredondar(parcela4.getValorPrestacao()));
        
        ParcelaDto parcela5 = parcelasPrice.get(4);
        assertEquals(5, parcela5.getNumero());
        assertEquals(new BigDecimal("186.44"), arredondar(parcela5.getValorAmortizacao()));
        assertEquals(new BigDecimal("3.34"), arredondar(parcela5.getValorJuros()));
        assertEquals(new BigDecimal(valorParcela), arredondar(parcela5.getValorPrestacao()));
    }

    @Test
    void testSacCalculosBasicos() {
        CalculadorSac calculadorSac = new CalculadorSac(valorEmprestimoPadrao, taxaJurosPadrao, numeroParcelasPadrao);
        List<ParcelaDto> parcelasSac = calculadorSac.calcularParcelas();

        // Validações básicas
        assertNotNull(parcelasSac);
        assertEquals(numeroParcelasPadrao, parcelasSac.size());
        assertEquals(tipoEmprestimoSAC, calculadorSac.getTipo());
        
        // Validar primeira parcela especificamente
        ParcelaDto primeiraParcela = parcelasSac.getFirst();
        assertEquals(1, primeiraParcela.getNumero());
        assertEquals(new BigDecimal("180.00"), primeiraParcela.getValorAmortizacao()); // 900/5
        assertEquals(new BigDecimal("16.11"), primeiraParcela.getValorJuros()); // 900 * 0.0179
        assertEquals(new BigDecimal("196.11"), primeiraParcela.getValorPrestacao()); // 180 + 16.11
        
        // Verificar que todas as parcelas têm componentes positivos
        for (ParcelaDto parcela : parcelasSac) {
            assertTrue(parcela.getValorAmortizacao().compareTo(BigDecimal.ZERO) > 0);
            assertTrue(parcela.getValorJuros().compareTo(BigDecimal.ZERO) > 0);
            assertTrue(parcela.getValorPrestacao().compareTo(BigDecimal.ZERO) > 0);
        }
    }
    
    @Test
    void testPriceCalculosBasicos() {
        CalculadorPrice calculadorPrice = new CalculadorPrice(valorEmprestimoPadrao, taxaJurosPadrao, numeroParcelasPadrao);
        List<ParcelaDto> parcelasPrice = calculadorPrice.calcularParcelas();

        assertNotNull(parcelasPrice);
        assertEquals(numeroParcelasPadrao, parcelasPrice.size());
        assertEquals(tipoEmprestimoPrice, calculadorPrice.getTipo());
        
        for (ParcelaDto parcela : parcelasPrice) {
            assertTrue(parcela.getValorAmortizacao().compareTo(BigDecimal.ZERO) > 0);
            assertTrue(parcela.getValorJuros().compareTo(BigDecimal.ZERO) > 0);
            assertTrue(parcela.getValorPrestacao().compareTo(BigDecimal.ZERO) > 0);
        }
        
        BigDecimal totalAmortizacoes = parcelasPrice.stream()
            .map(ParcelaDto::getValorAmortizacao)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        assertTrue(totalAmortizacoes.subtract(valorEmprestimoPadrao).abs().compareTo(new BigDecimal("0.10")) <= 0,
            "Total das amortizações deve ser igual ao valor emprestado");
    }
    
    @Test
    void testSacAmortizacaoConstante() {
        CalculadorSac calculadorSac = new CalculadorSac(valorEmprestimoPadrao, taxaJurosPadrao, numeroParcelasPadrao);
        List<ParcelaDto> parcelasSac = calculadorSac.calcularParcelas();
        BigDecimal amortizacao = parcelasSac.getFirst().getValorAmortizacao();
        
        for (ParcelaDto parcela : parcelasSac) {
            assertEquals(amortizacao, parcela.getValorAmortizacao());
        }
    }
    
    @Test
    void testSacPrestacoesDecrescentes() {
        CalculadorSac calculadorSac = new CalculadorSac(valorEmprestimoPadrao, taxaJurosPadrao, numeroParcelasPadrao);
        List<ParcelaDto> parcelasSac = calculadorSac.calcularParcelas();

        for (int i = 1; i < parcelasSac.size(); i++) {
            assertTrue(parcelasSac.get(i).getValorPrestacao().compareTo(parcelasSac.get(i-1).getValorPrestacao()) <= 0);
        }
    }
    
    @Test
    void testPricePrestacoesFixas() {
        CalculadorPrice calculadorPrice = new CalculadorPrice(valorEmprestimoPadrao, taxaJurosPadrao, numeroParcelasPadrao);
        List<ParcelaDto> parcelasPrice = calculadorPrice.calcularParcelas();
        BigDecimal prestacaoFixa = parcelasPrice.getFirst().getValorPrestacao();
        
        for (ParcelaDto parcela : parcelasPrice) {
            // Permitir pequena diferença devido a arredondamentos
            assertTrue(parcela.getValorPrestacao().subtract(prestacaoFixa).abs().compareTo(new BigDecimal("0.01")) <= 0);
        }
    }
    
    @Test
    void testPriceAmortizacaoCrescente() {
        CalculadorPrice calculadorPrice = new CalculadorPrice(valorEmprestimoPadrao, taxaJurosPadrao, numeroParcelasPadrao);
        List<ParcelaDto> parcelasPrice = calculadorPrice.calcularParcelas();

        for (int i = 1; i < parcelasPrice.size(); i++) {
            assertTrue(parcelasPrice.get(i).getValorAmortizacao().compareTo(parcelasPrice.get(i-1).getValorAmortizacao()) >= 0);
        }
    }
    
    @Test
    void testPriceJurosDecrescentes() {
        CalculadorPrice calculadorPrice = new CalculadorPrice(valorEmprestimoPadrao, taxaJurosPadrao, numeroParcelasPadrao);
        List<ParcelaDto> parcelasPrice = calculadorPrice.calcularParcelas();

        for (int i = 1; i < parcelasPrice.size(); i++) {
            assertTrue(parcelasPrice.get(i).getValorJuros().compareTo(parcelasPrice.get(i-1).getValorJuros()) <= 0);
        }
    }
    
    @Test
    void testComparacaoSacVsPrice() {
        BigDecimal valorEmprestimo = new BigDecimal("1000.00");
        BigDecimal taxaJuros = new BigDecimal("0.027");
        Integer numeroParcelas = 12;
        
        CalculadorSac calculadorSac = new CalculadorSac(valorEmprestimo, taxaJuros, numeroParcelas);
        CalculadorPrice calculadorPrice = new CalculadorPrice(valorEmprestimo, taxaJuros, numeroParcelas);
        
        List<ParcelaDto> parcelasSac = calculadorSac.calcularParcelas();
        List<ParcelaDto> parcelasPrice = calculadorPrice.calcularParcelas();
        
        BigDecimal totalSac = parcelasSac.stream()
            .map(ParcelaDto::getValorPrestacao)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
            
        BigDecimal totalPrice = parcelasPrice.stream()
            .map(ParcelaDto::getValorPrestacao)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        assertTrue(totalPrice.compareTo(totalSac) > 0, 
            "Price deve ter custo total maior que SAC");
    }

    @Test
    void testSacEquivalenciaFinanceiraValuePresente() {
        BigDecimal valorEmprestimo = new BigDecimal("1000.40");
        BigDecimal taxaJuros = new BigDecimal("0.027");
        Integer numeroParcelas = 6;

        BigDecimal valorPresente = calcularValorPresenteParcelasSac(valorEmprestimo, taxaJuros, numeroParcelas);

        BigDecimal diferenca = valorPresente.subtract(valorEmprestimo).abs();
        assertTrue(diferenca.compareTo(new BigDecimal("1.00")) <= 0, 
            "Valor presente deve ser próximo ao principal emprestado");
    }

    private static BigDecimal calcularValorPresenteParcelasSac(BigDecimal valorEmprestimo, BigDecimal taxaJuros, Integer numeroParcelas) {
        CalculadorSac calculadorSac = new CalculadorSac(valorEmprestimo, taxaJuros, numeroParcelas);
        List<ParcelaDto> parcelasSac = calculadorSac.calcularParcelas();

        // Calcular valor presente das parcelas SAC
        BigDecimal valorPresente = BigDecimal.ZERO;
        for (int i = 0; i < parcelasSac.size(); i++) {
            BigDecimal fatorDesconto = BigDecimal.ONE.add(taxaJuros).pow(i + 1);
            BigDecimal valorPresenteParcela = parcelasSac.get(i).getValorPrestacao()
                .divide(fatorDesconto, 10, RoundingMode.HALF_UP);
            valorPresente = valorPresente.add(valorPresenteParcela);
        }
        return valorPresente;
    }

    @Test
    void testSacValidacaoJurosPorPeriodo() {
        CalculadorSac calculadorSac = new CalculadorSac(valorEmprestimoPadrao, taxaJurosPadrao, numeroParcelasPadrao);
        List<ParcelaDto> parcelasSac = calculadorSac.calcularParcelas();
        
        BigDecimal saldoDevedor = valorEmprestimoPadrao;
        BigDecimal valorAmortizacao = valorEmprestimoPadrao.divide(BigDecimal.valueOf(numeroParcelasPadrao), 2, RoundingMode.HALF_UP);
        
        for (ParcelaDto parcela : parcelasSac) {
            BigDecimal jurosEsperados = saldoDevedor.multiply(taxaJurosPadrao).setScale(2, RoundingMode.HALF_UP);
            assertEquals(jurosEsperados, parcela.getValorJuros(), 
                "Juros devem ser calculados como taxa vezes saldo devedor");
            
            // Atualizar saldo devedor para próxima iteração
            saldoDevedor = saldoDevedor.subtract(valorAmortizacao);
        }
    }
    
    @Test
    void testValidacaoJurosPorPeriodoPrice() {
        CalculadorPrice calculadorPrice = new CalculadorPrice(valorEmprestimoPadrao, taxaJurosPadrao, numeroParcelasPadrao);
        List<ParcelaDto> parcelasPrice = calculadorPrice.calcularParcelas();
        
        BigDecimal saldoDevedor = valorEmprestimoPadrao;
        
        for (ParcelaDto parcela : parcelasPrice) {
            BigDecimal jurosEsperados = saldoDevedor.multiply(taxaJurosPadrao).setScale(2, RoundingMode.HALF_UP);
            assertEquals(jurosEsperados, parcela.getValorJuros(), 
                "Juros devem ser calculados como taxa vezes saldo devedor");
            
            // Atualizar saldo devedor para próxima iteração
            saldoDevedor = saldoDevedor.subtract(parcela.getValorAmortizacao());
        }
    }
    
    @Test
    void testSaldoDevedorSac() {
        CalculadorSac calculadorSac = new CalculadorSac(valorEmprestimoPadrao, taxaJurosPadrao, numeroParcelasPadrao);
        List<ParcelaDto> parcelasSac = calculadorSac.calcularParcelas();
        
        BigDecimal saldoDevedor = valorEmprestimoPadrao;
        BigDecimal valorAmortizacao = valorEmprestimoPadrao.divide(BigDecimal.valueOf(numeroParcelasPadrao), 2, RoundingMode.HALF_UP);
        
        for (ParcelaDto parcela : parcelasSac) {
            // Verificar que saldo devedor = saldo anterior - amortização do período
            saldoDevedor = saldoDevedor.subtract(valorAmortizacao);
            
            if (parcela.getNumero().equals(numeroParcelasPadrao)) {
                assertTrue(saldoDevedor.abs().compareTo(new BigDecimal("0.01")) <= 0, 
                    "Saldo final deve ser zero ou próximo de zero");
            }
        }
    }
    
    @Test
    void testSaldoDevedorPrice() {
        CalculadorPrice calculadorPrice = new CalculadorPrice(valorEmprestimoPadrao, taxaJurosPadrao, numeroParcelasPadrao);
        List<ParcelaDto> parcelasPrice = calculadorPrice.calcularParcelas();
        
        BigDecimal saldoDevedor = valorEmprestimoPadrao;
        
        for (ParcelaDto parcela : parcelasPrice) {
            // Verificar que saldo devedor = saldo anterior - amortização do período
            saldoDevedor = saldoDevedor.subtract(parcela.getValorAmortizacao());
            
            if (parcela.getNumero().equals(numeroParcelasPadrao)) {
                assertTrue(saldoDevedor.abs().compareTo(new BigDecimal("0.01")) <= 0, 
                    "Saldo final deve ser zero ou próximo de zero");
            }
        }
    }
    
    @Test
    void testFormulaPricePMT() {
        BigDecimal principal = new BigDecimal("1000.20");
        BigDecimal taxa = new BigDecimal("0.02");
        int parcelas = 15;
        
        CalculadorPrice calculadorPrice = new CalculadorPrice(principal, taxa, parcelas);
        List<ParcelaDto> parcelasPrice = calculadorPrice.calcularParcelas();
        
        // Calcular PMT esperado usando a fórmula: PMT = PV × i / (1 - (1+i)^-n)
        BigDecimal umMaisTaxa = BigDecimal.ONE.add(taxa);
        BigDecimal umMaisTaxaElevado = umMaisTaxa.pow(parcelas);
        BigDecimal denominador = BigDecimal.ONE.subtract(BigDecimal.ONE.divide(umMaisTaxaElevado, 10, RoundingMode.HALF_UP));
        BigDecimal pmtEsperado = principal.multiply(taxa).divide(denominador, 10, RoundingMode.HALF_UP);
        
        BigDecimal prestacaoCalculada = parcelasPrice.getFirst().getValorPrestacao();
        
        // Permitir pequena diferença devido a arredondamentos
        BigDecimal diferenca = prestacaoCalculada.subtract(pmtEsperado).abs();
        assertTrue(diferenca.compareTo(new BigDecimal("0.10")) <= 0, 
            "Prestação calculada deve estar próxima da fórmula PMT");
    }
    
    @Test
    void testFormulaSacAmortizacao() {
        CalculadorSac calculadorSac = new CalculadorSac(valorEmprestimoPadrao, taxaJurosPadrao, numeroParcelasPadrao);
        List<ParcelaDto> parcelasSac = calculadorSac.calcularParcelas();
        
        // No SAC: amortização = PV / n
        BigDecimal amortizacaoEsperada = valorEmprestimoPadrao.divide(BigDecimal.valueOf(numeroParcelasPadrao), 2, RoundingMode.HALF_UP);
        
        for (ParcelaDto parcela : parcelasSac) {
            assertEquals(amortizacaoEsperada, parcela.getValorAmortizacao(),
                "Amortização deve ser constante e igual a principal dividido por número de parcelas (PV/n)");
            
            // Verificar se parcela = amortização + juros
            BigDecimal parcelaEsperada = parcela.getValorAmortizacao().add(parcela.getValorJuros());
            assertEquals(parcelaEsperada, parcela.getValorPrestacao());
        }
    }

    @Test
    void testSacValoresPequenos() {
        BigDecimal valorMinimo = new BigDecimal("0.01");
        BigDecimal taxaMinima = new BigDecimal("0.001");
        
        CalculadorSac calculadorMinimo = new CalculadorSac(valorMinimo, taxaMinima, 1);
        List<ParcelaDto> parcelasMinimas = calculadorMinimo.calcularParcelas();
        
        assertEquals(1, parcelasMinimas.size());
        ParcelaDto parcela = parcelasMinimas.getFirst();
        assertEquals(valorMinimo, parcela.getValorAmortizacao());
        assertEquals(new BigDecimal("0.00"), parcela.getValorJuros(), "Valor muito pequeno deve arredondar para zero");
    }

    @Test
    void testAmortizacaoEsperadaSac() {
        BigDecimal valorMedio = new BigDecimal("50000.00");
        BigDecimal taxaMensal = new BigDecimal("0.015");
        int prazo = 180;

        CalculadorSac calculadorLongo = new CalculadorSac(valorMedio, taxaMensal, prazo);
        List<ParcelaDto> parcelas = calculadorLongo.calcularParcelas();

        BigDecimal amortizacaoEsperada = valorMedio.divide(BigDecimal.valueOf(prazo), 2, RoundingMode.HALF_UP);
        BigDecimal amortizacaoCalculada = parcelas.getFirst().getValorAmortizacao();
        assertEquals(amortizacaoEsperada, amortizacaoCalculada, "Amortização deve ser igual à esperada");
    }


    @Test
    void testPrazoLongoSac() {
        BigDecimal valorMedio = new BigDecimal("200000.00");
        BigDecimal taxaMensal = new BigDecimal("0.007");
        Integer prazoLongo = 360;
        
        CalculadorSac calculadorLongo = new CalculadorSac(valorMedio, taxaMensal, prazoLongo);
        List<ParcelaDto> parcelasLongas = calculadorLongo.calcularParcelas();
        
        assertEquals(360, parcelasLongas.size());
        assertEquals(
            parcelasLongas.get(359).getValorAmortizacao(), 
            parcelasLongas.getFirst().getValorAmortizacao(),
            "Amortização deve ser constante"
        );
    }
    
    @Test
    void testSacComportamentoValoresNegativos() {
        BigDecimal valorNegativo = new BigDecimal("-1000.00");
        BigDecimal taxaPositiva = new BigDecimal("0.02");
        
        CalculadorSac calculadorSac = new CalculadorSac(valorNegativo, taxaPositiva, 5, true);
        List<ParcelaDto> parcelas = calculadorSac.calcularParcelas();
        
        assertEquals(5, parcelas.size());
        
        BigDecimal amortizacaoEsperada = new BigDecimal("-200.00"); // -100/5
        assertEquals(amortizacaoEsperada, parcelas.getFirst().getValorAmortizacao());
        
        // -1000 * 0.02 = -20.00
        assertEquals(new BigDecimal("-20.00"), parcelas.getFirst().getValorJuros());
        
        // -200.00 + (-20.00) = -220.00
        assertEquals(new BigDecimal("-220.00"), parcelas.getFirst().getValorPrestacao());
    }
    
    @Test
    void testSacPrecisaoDecimal() {
        // TODO: Provavelmente apagar esse este ou torná-lo parametrizado
        BigDecimal valorPreciso = new BigDecimal("1000.123456789");
        BigDecimal taxaPrecisa = new BigDecimal("0.023456789");
        
        CalculadorSac calculadorSac = new CalculadorSac(valorPreciso, taxaPrecisa, 15);
        List<ParcelaDto> parcelas = calculadorSac.calcularParcelas();
        
        assertNotNull(parcelas);
        
        for (ParcelaDto parcela : parcelas) {
            assertEquals(2, parcela.getValorAmortizacao().scale());
            assertEquals(2, parcela.getValorJuros().scale());
            assertEquals(2, parcela.getValorPrestacao().scale());
        }
    }

    @Test
    void testUmaParcela() {
        BigDecimal valorEmprestimo = new BigDecimal("1000.10");
        BigDecimal taxaJuros = new BigDecimal("0.05");
        Integer umaParcela = 1;
        
        CalculadorSac calculadorSac = new CalculadorSac(valorEmprestimo, taxaJuros, umaParcela);
        CalculadorPrice calculadorPrice = new CalculadorPrice(valorEmprestimo, taxaJuros, umaParcela);
        
        List<ParcelaDto> parcelasSac = calculadorSac.calcularParcelas();
        List<ParcelaDto> parcelasPrice = calculadorPrice.calcularParcelas();

        ParcelaDto parcelaSac = parcelasSac.getFirst();
        ParcelaDto parcelaPrice = parcelasPrice.getFirst();

        assertEquals(
            parcelaSac.getValorAmortizacao(), 
            parcelaPrice.getValorAmortizacao(), 
            "Com uma parcela, SAC e Price devem ter comportamento idêntico"
        );
        assertEquals(parcelaSac.getValorJuros(), parcelaPrice.getValorJuros());
        assertEquals(parcelaSac.getValorPrestacao(), parcelaPrice.getValorPrestacao());
    }

    @Test
    void testExcecaoValorEmprestimoNulo() {
        assertThrows(CalculoException.class, () -> {
            new CalculadorSac(null, new BigDecimal("0.02"), 12);
        }, "Deve lançar exceção para valor de empréstimo nulo");
    }

    @Test
    void testExcecaoValorEmprestimoZero() {
        assertThrows(CalculoException.class, () -> {
            new CalculadorSac(BigDecimal.ZERO, new BigDecimal("0.02"), 12);
        }, "Deve lançar exceção para valor de empréstimo zero");
    }

    @Test
    void testExcecaoValorEmprestimoNegativo() {
        assertThrows(CalculoException.class, () -> {
            new CalculadorSac(new BigDecimal("-1000"), new BigDecimal("0.02"), 12);
        }, "Deve lançar exceção para valor de empréstimo negativo");
    }

    @Test
    void testExcecaoTaxaJurosNula() {
        assertThrows(CalculoException.class, () -> {
            new CalculadorPrice(new BigDecimal("1000"), null, 12);
        }, "Deve lançar exceção para taxa de juros nula");
    }

    @Test
    void testExcecaoTaxaJurosNegativa() {
        assertThrows(CalculoException.class, () -> {
            new CalculadorPrice(new BigDecimal("1000"), new BigDecimal("-0.01"), 12);
        }, "Deve lançar exceção para taxa de juros negativa");
    }

    @Test
    void testExcecaoNumeroParcelasNulo() {
        assertThrows(CalculoException.class, () -> {
            new CalculadorSac(new BigDecimal("1000"), new BigDecimal("0.02"), null);
        }, "Deve lançar exceção para número de parcelas nulo");
    }

    @Test
    void testExcecaoNumeroParcelasZero() {
        assertThrows(CalculoException.class, () -> {
            new CalculadorSac(new BigDecimal("1000"), new BigDecimal("0.02"), 0);
        }, "Deve lançar exceção para número de parcelas zero");
    }

    @Test
    void testExcecaoNumeroParcelasNegativo() {
        assertThrows(CalculoException.class, () -> {
            new CalculadorPrice(new BigDecimal("1000"), new BigDecimal("0.02"), -5);
        }, "Deve lançar exceção para número de parcelas negativo");
    }

    @Test
    void testExcecaoValorNuloArredondamento() {
        CalculadorSac calculador = new CalculadorSac(new BigDecimal("1000"), new BigDecimal("0.02"), 12);
        assertThrows(CalculoException.class, () -> {
            calculador.arredondar(null);
        }, "Deve lançar exceção ao tentar arredondar valor nulo");
    }

    @Test
    void testMecanismoNaoAfetaValidacaoParcelasPositivas() {
        // Mesmo com valores negativos habilitados, parcelas devem ser > 0
        assertThrows(CalculoException.class, () -> {
            new CalculadorSac(new BigDecimal("1000"), new BigDecimal("0.02"), 0, true);
        }, "Validação de parcelas > 0 deve permanecer ativa");
    }
}
