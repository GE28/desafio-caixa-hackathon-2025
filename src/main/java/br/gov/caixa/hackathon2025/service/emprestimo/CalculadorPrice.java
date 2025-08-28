package br.gov.caixa.hackathon2025.service.emprestimo;

import br.gov.caixa.hackathon2025.dto.ParcelaDto;
import br.gov.caixa.hackathon2025.exception.CalculoException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class CalculadorPrice extends CalculadorEmprestimo {
    
    public CalculadorPrice(BigDecimal valorEmprestimo, BigDecimal taxaJuros, Integer numeroParcelas) {
        super(valorEmprestimo, taxaJuros, numeroParcelas);
    }
    
    public CalculadorPrice(BigDecimal valorEmprestimo, BigDecimal taxaJuros, Integer numeroParcelas, boolean permitirValoresNegativos) {
        super(valorEmprestimo, taxaJuros, numeroParcelas, permitirValoresNegativos);
    }
    
    @Override
    public List<ParcelaDto> calcularParcelas() {
        List<ParcelaDto> parcelas = new ArrayList<>();
        
        try {
            BigDecimal prestacaoFixa = calcularPrestacaoFixa();
            BigDecimal saldoDevedor = valorEmprestimo;
            
            for (int i = 1; i <= numeroParcelas; i++) {
                BigDecimal valorJuros = arredondar(saldoDevedor.multiply(taxaJuros));
                BigDecimal valorAmortizacao = arredondar(prestacaoFixa.subtract(valorJuros));
                
                parcelas.add(new ParcelaDto(i, valorAmortizacao, valorJuros, arredondar(prestacaoFixa)));
                
                saldoDevedor = saldoDevedor.subtract(valorAmortizacao);
            }
            
            validarResultado(parcelas);
            
        } catch (ArithmeticException e) {
            throw new CalculoException("Erro aritmético no cálculo PRICE: " + e.getMessage(), e);
        } catch (Exception e) {
            if (e instanceof CalculoException) {
                throw e;
            }
            throw new CalculoException("Erro inesperado no cálculo PRICE: " + e.getMessage(), e);
        }
        
        return parcelas;
    }
    
    private BigDecimal calcularPrestacaoFixa() {
        try {
            MathContext mc = new MathContext(10, RoundingMode.HALF_UP);
            
            if (taxaJuros.compareTo(BigDecimal.ZERO) == 0) {
                return valorEmprestimo.divide(BigDecimal.valueOf(numeroParcelas), mc);
            }
            
            BigDecimal umMaisTaxa = BigDecimal.ONE.add(taxaJuros, mc);
            BigDecimal umMaisTaxaElevado = umMaisTaxa.pow(numeroParcelas, mc);
            
            BigDecimal numerador = taxaJuros.multiply(umMaisTaxaElevado, mc);
            BigDecimal denominador = umMaisTaxaElevado.subtract(BigDecimal.ONE, mc);
            
            if (denominador.compareTo(BigDecimal.ZERO) == 0) {
                throw new CalculoException("Divisão por zero no cálculo da prestação fixa PRICE");
            }
            
            BigDecimal fator = numerador.divide(denominador, mc);
            return valorEmprestimo.multiply(fator, mc);
            
        } catch (ArithmeticException e) {
            throw new CalculoException("Erro aritmético no cálculo da prestação fixa PRICE: " + e.getMessage(), e);
        }
    }
    
    @Override
    public String getTipo() {
        return "PRICE";
    }
}
