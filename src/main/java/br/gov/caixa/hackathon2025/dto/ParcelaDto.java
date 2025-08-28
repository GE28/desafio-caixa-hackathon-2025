package br.gov.caixa.hackathon2025.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import java.math.BigDecimal;

@Schema(description = "Detalhes de uma parcela do empréstimo")
public class ParcelaDto {
    
    @JsonProperty("numero")
    @Schema(description = "Número da parcela", example = "1")
    private Integer numero;
    
    @JsonProperty("valorAmortizacao")
    @Schema(description = "Valor da amortização", example = "2083.33")
    private BigDecimal valorAmortizacao;
    
    @JsonProperty("valorJuros")
    @Schema(description = "Valor dos juros", example = "750.00")
    private BigDecimal valorJuros;
    
    @JsonProperty("valorPrestacao")
    @Schema(description = "Valor total da prestação", example = "2833.33")
    private BigDecimal valorPrestacao;
    
    // Constructors
    public ParcelaDto() {}
    
    public ParcelaDto(Integer numero, BigDecimal valorAmortizacao, BigDecimal valorJuros, BigDecimal valorPrestacao) {
        this.numero = numero;
        this.valorAmortizacao = valorAmortizacao;
        this.valorJuros = valorJuros;
        this.valorPrestacao = valorPrestacao;
    }
    
    // Getters and Setters
    public Integer getNumero() {
        return numero;
    }
    
    public void setNumero(Integer numero) {
        this.numero = numero;
    }
    
    public BigDecimal getValorAmortizacao() {
        return valorAmortizacao;
    }
    
    public void setValorAmortizacao(BigDecimal valorAmortizacao) {
        this.valorAmortizacao = valorAmortizacao;
    }
    
    public BigDecimal getValorJuros() {
        return valorJuros;
    }
    
    public void setValorJuros(BigDecimal valorJuros) {
        this.valorJuros = valorJuros;
    }
    
    public BigDecimal getValorPrestacao() {
        return valorPrestacao;
    }
    
    public void setValorPrestacao(BigDecimal valorPrestacao) {
        this.valorPrestacao = valorPrestacao;
    }
}
