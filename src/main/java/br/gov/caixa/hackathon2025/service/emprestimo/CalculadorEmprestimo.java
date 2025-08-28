package br.gov.caixa.hackathon2025.service.emprestimo;

import br.gov.caixa.hackathon2025.dto.ParcelaDto;
import br.gov.caixa.hackathon2025.exception.CalculoException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public abstract class CalculadorEmprestimo {
    
    protected BigDecimal valorEmprestimo;
    protected BigDecimal taxaJuros;
    protected Integer numeroParcelas;
    protected boolean permitirValoresNegativos = false;
    
    protected CalculadorEmprestimo(BigDecimal valorEmprestimo, BigDecimal taxaJuros, Integer numeroParcelas) {
        validarParametros(valorEmprestimo, taxaJuros, numeroParcelas);
        this.valorEmprestimo = valorEmprestimo;
        this.taxaJuros = taxaJuros;
        this.numeroParcelas = numeroParcelas;
    }
    
    protected CalculadorEmprestimo(BigDecimal valorEmprestimo, BigDecimal taxaJuros, Integer numeroParcelas, boolean permitirValoresNegativos) {
        this.permitirValoresNegativos = permitirValoresNegativos;
        validarParametros(valorEmprestimo, taxaJuros, numeroParcelas);
        this.valorEmprestimo = valorEmprestimo;
        this.taxaJuros = taxaJuros;
        this.numeroParcelas = numeroParcelas;
    }
    
    private void validarParametros(BigDecimal valorEmprestimo, BigDecimal taxaJuros, Integer numeroParcelas) {
        if (valorEmprestimo == null || (!permitirValoresNegativos && valorEmprestimo.compareTo(BigDecimal.ZERO) <= 0)) {
            throw new CalculoException("Valor do empréstimo deve ser maior que zero");
        }
        if (taxaJuros == null || (!permitirValoresNegativos && taxaJuros.compareTo(BigDecimal.ZERO) < 0)) {
            throw new CalculoException("Taxa de juros deve ser maior ou igual a zero");
        }
        if (numeroParcelas == null || numeroParcelas <= 0) {
            throw new CalculoException("Número de parcelas deve ser maior que zero");
        }
    }
    
    public abstract List<ParcelaDto> calcularParcelas();
    
    public abstract String getTipo();
    
    protected BigDecimal arredondar(BigDecimal valor) {
        if (valor == null) {
            throw new CalculoException("Valor nulo não pode ser arredondado");
        }
        return valor.setScale(2, RoundingMode.HALF_UP);
    }
    
    protected void validarResultado(List<ParcelaDto> parcelas) {
        if (parcelas == null || parcelas.isEmpty()) {
            throw new CalculoException("Falha no cálculo: nenhuma parcela foi gerada");
        }
        if (parcelas.size() != numeroParcelas) {
            throw new CalculoException(String.format(
                "Falha no cálculo: esperado %d parcelas, calculado %d", 
                numeroParcelas, parcelas.size()));
        }
    }
}
