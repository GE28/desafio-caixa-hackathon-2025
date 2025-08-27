package br.gov.caixa.hackathon2025.service.emprestimo;

import io.quarkus.test.junit.QuarkusTest;
import br.gov.caixa.hackathon2025.dto.ParcelaDto;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class CalculadorEmprestimoTest {
    private final BigDecimal VALOR_EMPRESTIMO_PADRAO = new BigDecimal("900.00");
    private final BigDecimal TAXA_JUROS_PADRAO = new BigDecimal("0.0179");
    private final Integer NUMERO_PARCELAS_PADRAO = 5;
    // Literals têm seu uso limitado por ferramentas de análise como SonarQube.
    // Nesses casos, é boa prática utilizar constantes nomeadas.
    private final String TIPO_EMPRESTIMO_SAC = "SAC";
    private final String TIPO_EMPRESTIMO_PRICE = "PRICE";

    @Test
    public void testSacBasico() {
        CalculadorSac calculadorSac = new CalculadorSac(VALOR_EMPRESTIMO_PADRAO, TAXA_JUROS_PADRAO, NUMERO_PARCELAS_PADRAO);
        List<ParcelaDto> parcelasSac = calculadorSac.calcularParcelas();

        assertNotNull(parcelasSac);
        assertEquals(NUMERO_PARCELAS_PADRAO, parcelasSac.size());
        assertEquals(TIPO_EMPRESTIMO_SAC, calculadorSac.getTipo());
    }
    
    @Test
    public void testSacPrimeiraParcela() {
        CalculadorSac calculadorSac = new CalculadorSac(VALOR_EMPRESTIMO_PADRAO, TAXA_JUROS_PADRAO, NUMERO_PARCELAS_PADRAO);
        List<ParcelaDto> parcelasSac = calculadorSac.calcularParcelas();
        ParcelaDto primeiraParcela = parcelasSac.getFirst();
        
        assertEquals(1, primeiraParcela.getNumero());
        assertEquals(new BigDecimal("180.00"), primeiraParcela.getValorAmortizacao());
        assertTrue(primeiraParcela.getValorJuros().compareTo(BigDecimal.ZERO) > 0);
        assertTrue(primeiraParcela.getValorPrestacao().compareTo(BigDecimal.ZERO) > 0);
    }
    
    @Test
    public void testSacAmortizacaoConstante() {
        CalculadorSac calculadorSac = new CalculadorSac(VALOR_EMPRESTIMO_PADRAO, TAXA_JUROS_PADRAO, NUMERO_PARCELAS_PADRAO);
        List<ParcelaDto> parcelasSac = calculadorSac.calcularParcelas();
        BigDecimal amortizacao = parcelasSac.getFirst().getValorAmortizacao();
        
        for (ParcelaDto parcela : parcelasSac) {
            assertEquals(amortizacao, parcela.getValorAmortizacao());
        }
    }
    
    @Test
    public void testSacPrestacoesDecrescentes() {
        CalculadorSac calculadorSac = new CalculadorSac(VALOR_EMPRESTIMO_PADRAO, TAXA_JUROS_PADRAO, NUMERO_PARCELAS_PADRAO);
        List<ParcelaDto> parcelasSac = calculadorSac.calcularParcelas();

        for (int i = 1; i < parcelasSac.size(); i++) {
            assertTrue(parcelasSac.get(i).getValorPrestacao().compareTo(parcelasSac.get(i-1).getValorPrestacao()) <= 0);
        }
    }
    
    @Test
    public void testPriceBasico() {
        CalculadorPrice calculadorPrice = new CalculadorPrice(VALOR_EMPRESTIMO_PADRAO, TAXA_JUROS_PADRAO, NUMERO_PARCELAS_PADRAO);
        List<ParcelaDto> parcelasPrice = calculadorPrice.calcularParcelas();

        assertNotNull(parcelasPrice);
        assertEquals(5, parcelasPrice.size());
        assertEquals(TIPO_EMPRESTIMO_PRICE, calculadorPrice.getTipo());
    }
    
    @Test
    public void testPricePrestacoesFixas() {
        CalculadorPrice calculadorPrice = new CalculadorPrice(VALOR_EMPRESTIMO_PADRAO, TAXA_JUROS_PADRAO, NUMERO_PARCELAS_PADRAO);
        List<ParcelaDto> parcelasPrice = calculadorPrice.calcularParcelas();
        BigDecimal prestacaoFixa = parcelasPrice.getFirst().getValorPrestacao();
        
        for (ParcelaDto parcela : parcelasPrice) {
            // Permitir pequena diferença devido a arredondamentos
            assertTrue(parcela.getValorPrestacao().subtract(prestacaoFixa).abs().compareTo(new BigDecimal("0.01")) <= 0);
        }
    }
    
    @Test
    public void testPriceAmortizacaoCrescente() {
        CalculadorPrice calculadorPrice = new CalculadorPrice(VALOR_EMPRESTIMO_PADRAO, TAXA_JUROS_PADRAO, NUMERO_PARCELAS_PADRAO);
        List<ParcelaDto> parcelasPrice = calculadorPrice.calcularParcelas();

        for (int i = 1; i < parcelasPrice.size(); i++) {
            assertTrue(parcelasPrice.get(i).getValorAmortizacao().compareTo(parcelasPrice.get(i-1).getValorAmortizacao()) >= 0);
        }
    }
    
    @Test
    public void testPriceJurosDecrescentes() {
        CalculadorPrice calculadorPrice = new CalculadorPrice(VALOR_EMPRESTIMO_PADRAO, TAXA_JUROS_PADRAO, NUMERO_PARCELAS_PADRAO);
        List<ParcelaDto> parcelasPrice = calculadorPrice.calcularParcelas();

        for (int i = 1; i < parcelasPrice.size(); i++) {
            assertTrue(parcelasPrice.get(i).getValorJuros().compareTo(parcelasPrice.get(i-1).getValorJuros()) <= 0);
        }
    }
    
    @Test
    public void testPrincipioEquivalenciaFinanceira() {
        BigDecimal valorEmprestimo = new BigDecimal("1000.00");
        BigDecimal taxaJuros = new BigDecimal("0.02");
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
        
        // PRICE sempre resulta em valor total maior que SAC
        assertTrue(totalPrice.compareTo(totalSac) > 0);
    }
}
