package br.gov.caixa.hackathon2025.service.emprestimo;

import br.gov.caixa.hackathon2025.dto.ParcelaDto;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public abstract class CalculadorEmprestimo {
    
    protected BigDecimal valorEmprestimo;
    protected BigDecimal taxaJuros;
    protected Integer numeroParcelas;
    
    public CalculadorEmprestimo(BigDecimal valorEmprestimo, BigDecimal taxaJuros, Integer numeroParcelas) {
        this.valorEmprestimo = valorEmprestimo;
        this.taxaJuros = taxaJuros;
        this.numeroParcelas = numeroParcelas;
    }
    
    public abstract List<ParcelaDto> calcularParcelas();
    
    public abstract String getTipo();
    
    protected BigDecimal arredondar(BigDecimal valor) {
        return valor.setScale(2, RoundingMode.HALF_UP);
    }
}
