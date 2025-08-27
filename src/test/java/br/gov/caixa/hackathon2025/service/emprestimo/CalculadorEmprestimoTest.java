package br.gov.caixa.hackathon2025.service.emprestimo;

import io.quarkus.test.junit.QuarkusTest;
import br.gov.caixa.hackathon2025.dto.ParcelaDto;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class CalculadorEmprestimoTest {
    
    @Test
    public void testCalculadorSac() {
        // Dados do exemplo das instruções
        BigDecimal valorEmprestimo = new BigDecimal("900.00");
        BigDecimal taxaJuros = new BigDecimal("0.017900000");
        Integer numeroParcelas = 5;
        
        CalculadorSac calculador = new CalculadorSac(valorEmprestimo, taxaJuros, numeroParcelas);
        List<ParcelaDto> parcelas = calculador.calcularParcelas();
        
        assertNotNull(parcelas);
        assertEquals(5, parcelas.size());
        assertEquals("SAC", calculador.getTipo());
        
        // Verificar primeira parcela
        ParcelaDto primeiraParcela = parcelas.get(0);
        assertEquals(1, primeiraParcela.getNumero());
        assertEquals(new BigDecimal("180.00"), primeiraParcela.getValorAmortizacao());
        assertTrue(primeiraParcela.getValorJuros().compareTo(BigDecimal.ZERO) > 0);
        assertTrue(primeiraParcela.getValorPrestacao().compareTo(BigDecimal.ZERO) > 0);
        
        // No SAC, a amortização deve ser constante
        BigDecimal amortizacao = parcelas.get(0).getValorAmortizacao();
        for (ParcelaDto parcela : parcelas) {
            assertEquals(amortizacao, parcela.getValorAmortizacao());
        }
        
        // No SAC, as prestações devem ser decrescentes
        for (int i = 1; i < parcelas.size(); i++) {
            assertTrue(parcelas.get(i).getValorPrestacao().compareTo(parcelas.get(i-1).getValorPrestacao()) <= 0);
        }
    }
    
    @Test
    public void testCalculadorPrice() {
        BigDecimal valorEmprestimo = new BigDecimal("900.00");
        BigDecimal taxaJuros = new BigDecimal("0.017900000");
        Integer numeroParcelas = 5;
        
        CalculadorPrice calculador = new CalculadorPrice(valorEmprestimo, taxaJuros, numeroParcelas);
        List<ParcelaDto> parcelas = calculador.calcularParcelas();
        
        assertNotNull(parcelas);
        assertEquals(5, parcelas.size());
        assertEquals("PRICE", calculador.getTipo());
        
        // No PRICE, as prestações devem ser fixas (ou muito próximas devido a arredondamentos)
        BigDecimal prestacaoFixa = parcelas.get(0).getValorPrestacao();
        for (ParcelaDto parcela : parcelas) {
            // Permitir pequena diferença devido a arredondamentos
            assertTrue(parcela.getValorPrestacao().subtract(prestacaoFixa).abs().compareTo(new BigDecimal("0.01")) <= 0);
        }
        
        // No PRICE, a amortização deve ser crescente
        for (int i = 1; i < parcelas.size(); i++) {
            assertTrue(parcelas.get(i).getValorAmortizacao().compareTo(parcelas.get(i-1).getValorAmortizacao()) >= 0);
        }
        
        // No PRICE, os juros devem ser decrescentes
        for (int i = 1; i < parcelas.size(); i++) {
            assertTrue(parcelas.get(i).getValorJuros().compareTo(parcelas.get(i-1).getValorJuros()) <= 0);
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
        
        // Ambos devem resultar no mesmo valor total
        BigDecimal totalSac = parcelasSac.stream()
            .map(ParcelaDto::getValorPrestacao)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
            
        BigDecimal totalPrice = parcelasPrice.stream()
            .map(ParcelaDto::getValorPrestacao)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        // Deve haver diferença mínima entre os totais (devido a arredondamentos)
        assertTrue(totalSac.subtract(totalPrice).abs().compareTo(new BigDecimal("1.00")) <= 0);
    }
}
