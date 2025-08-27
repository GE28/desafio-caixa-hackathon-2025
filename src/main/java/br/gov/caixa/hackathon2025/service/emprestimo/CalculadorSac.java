package br.gov.caixa.hackathon2025.service.emprestimo;

import br.gov.caixa.hackathon2025.dto.ParcelaDto;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class CalculadorSac extends CalculadorEmprestimo {
    
    public CalculadorSac(BigDecimal valorEmprestimo, BigDecimal taxaJuros, Integer numeroParcelas) {
        super(valorEmprestimo, taxaJuros, numeroParcelas);
    }
    
    @Override
    public List<ParcelaDto> calcularParcelas() {
        List<ParcelaDto> parcelas = new ArrayList<>();
        
        // No SAC, a amortização é constante
        BigDecimal valorAmortizacao = valorEmprestimo.divide(BigDecimal.valueOf(numeroParcelas), 2, RoundingMode.HALF_UP);
        BigDecimal saldoDevedor = valorEmprestimo;
        
        for (int i = 1; i <= numeroParcelas; i++) {
            // Juros = Taxa * Saldo Devedor
            BigDecimal valorJuros = arredondar(saldoDevedor.multiply(taxaJuros));
            
            // Prestação = Amortização + Juros
            BigDecimal valorPrestacao = arredondar(valorAmortizacao.add(valorJuros));
            
            parcelas.add(new ParcelaDto(i, arredondar(valorAmortizacao), valorJuros, valorPrestacao));
            
            // Atualiza o saldo devedor
            saldoDevedor = saldoDevedor.subtract(valorAmortizacao);
        }
        
        return parcelas;
    }
    
    @Override
    public String getTipo() {
        return "SAC";
    }
}
