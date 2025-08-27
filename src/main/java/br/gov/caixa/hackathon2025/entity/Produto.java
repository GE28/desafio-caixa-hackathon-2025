package br.gov.caixa.hackathon2025.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "PRODUTO", schema = "dbo")
public class Produto {
    
    @Id
    @Column(name = "CO_PRODUTO")
    private Integer codigoProduto;
    
    @Column(name = "NO_PRODUTO", length = 200, nullable = false)
    private String nomeProduto;
    
    @Column(name = "PC_TAXA_JUROS", precision = 10, scale = 9, nullable = false)
    private BigDecimal percentualTaxaJuros;
    
    @Column(name = "NU_MINIMO_MESES", nullable = false)
    private Short numeroMinimoMeses;
    
    @Column(name = "NU_MAXIMO_MESES")
    private Short numeroMaximoMeses;
    
    @Column(name = "VR_MINIMO", precision = 18, scale = 2, nullable = false)
    private BigDecimal valorMinimo;
    
    @Column(name = "VR_MAXIMO", precision = 18, scale = 2)
    private BigDecimal valorMaximo;
    
    // Constructors
    public Produto() {}
    
    // Getters and Setters
    public Integer getCodigoProduto() {
        return codigoProduto;
    }
    
    public void setCodigoProduto(Integer codigoProduto) {
        this.codigoProduto = codigoProduto;
    }
    
    public String getNomeProduto() {
        return nomeProduto;
    }
    
    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }
    
    public BigDecimal getPercentualTaxaJuros() {
        return percentualTaxaJuros;
    }
    
    public void setPercentualTaxaJuros(BigDecimal percentualTaxaJuros) {
        this.percentualTaxaJuros = percentualTaxaJuros;
    }
    
    public Short getNumeroMinimoMeses() {
        return numeroMinimoMeses;
    }
    
    public void setNumeroMinimoMeses(Short numeroMinimoMeses) {
        this.numeroMinimoMeses = numeroMinimoMeses;
    }
    
    public Short getNumeroMaximoMeses() {
        return numeroMaximoMeses;
    }
    
    public void setNumeroMaximoMeses(Short numeroMaximoMeses) {
        this.numeroMaximoMeses = numeroMaximoMeses;
    }
    
    public BigDecimal getValorMinimo() {
        return valorMinimo;
    }
    
    public void setValorMinimo(BigDecimal valorMinimo) {
        this.valorMinimo = valorMinimo;
    }
    
    public BigDecimal getValorMaximo() {
        return valorMaximo;
    }
    
    public void setValorMaximo(BigDecimal valorMaximo) {
        this.valorMaximo = valorMaximo;
    }
}
