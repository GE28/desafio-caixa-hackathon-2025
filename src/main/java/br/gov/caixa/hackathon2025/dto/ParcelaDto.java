package br.gov.caixa.hackathon2025.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class ParcelaDto {
    
    @JsonProperty("numero")
    private Integer numero;
    
    @JsonProperty("valorAmortizacao")
    private BigDecimal valorAmortizacao;
    
    @JsonProperty("valorJuros")
    private BigDecimal valorJuros;
    
    @JsonProperty("valorPrestacao")
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
