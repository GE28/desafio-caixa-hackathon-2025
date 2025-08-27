package br.gov.caixa.hackathon2025.service.emprestimo;

import br.gov.caixa.hackathon2025.dto.ParcelaDto;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class CalculadorPrice extends CalculadorEmprestimo {
    
    public CalculadorPrice(BigDecimal valorEmprestimo, BigDecimal taxaJuros, Integer numeroParcelas) {
        super(valorEmprestimo, taxaJuros, numeroParcelas);
    }
    
    @Override
    public List<ParcelaDto> calcularParcelas() {
        List<ParcelaDto> parcelas = new ArrayList<>();
        
        // Price utiliza prestação fixa
        BigDecimal prestacaoFixa = calcularPrestacaoFixa();
        BigDecimal saldoDevedor = valorEmprestimo;
        
        for (int i = 1; i <= numeroParcelas; i++) {
            // Juros = Taxa * Saldo Devedor
            BigDecimal valorJuros = arredondar(saldoDevedor.multiply(taxaJuros));
            
            // Amortização = Prestação - Juros
            BigDecimal valorAmortizacao = arredondar(prestacaoFixa.subtract(valorJuros));
            
            parcelas.add(new ParcelaDto(i, valorAmortizacao, valorJuros, arredondar(prestacaoFixa)));
            
            // Atualiza o saldo devedor
            saldoDevedor = saldoDevedor.subtract(valorAmortizacao);
        }
        
        return parcelas;
    }
    
    private BigDecimal calcularPrestacaoFixa() {
        MathContext mc = new MathContext(10, RoundingMode.HALF_UP);
        
        // PMT = PV * [i * (1 + i)^n] / [(1 + i)^n - 1]
        BigDecimal umMaisTaxa = BigDecimal.ONE.add(taxaJuros, mc);
        BigDecimal umMaisTaxaElevado = umMaisTaxa.pow(numeroParcelas, mc);
        
        BigDecimal numerador = taxaJuros.multiply(umMaisTaxaElevado, mc);
        BigDecimal denominador = umMaisTaxaElevado.subtract(BigDecimal.ONE, mc);
        
        BigDecimal fator = numerador.divide(denominador, mc);
        return valorEmprestimo.multiply(fator, mc);
    }
    
    @Override
    public String getTipo() {
        return "PRICE";
    }
}
