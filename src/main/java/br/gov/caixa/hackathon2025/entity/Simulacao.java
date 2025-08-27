package br.gov.caixa.hackathon2025.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "simulacao")
public class Simulacao {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_simulacao")
    private Long idSimulacao;
    
    @Column(name = "codigo_produto", nullable = false)
    private Integer codigoProduto;
    
    @Column(name = "valor_desejado", precision = 18, scale = 2, nullable = false)
    private BigDecimal valorDesejado;
    
    @Column(name = "prazo", nullable = false)
    private Integer prazo;
    
    @Column(name = "taxa_juros", precision = 10, scale = 9, nullable = false)
    private BigDecimal taxaJuros;
    
    @Column(name = "valor_total_parcelas", precision = 18, scale = 2, nullable = false)
    private BigDecimal valorTotalParcelas;
    
    @Column(name = "data_simulacao", nullable = false)
    private LocalDateTime dataSimulacao;
    
    @Column(name = "tempo_resposta_ms")
    private Long tempoRespostaMilissegundos;
    
    // Constructors
    public Simulacao() {
        this.dataSimulacao = LocalDateTime.now();
    }
    
    // Getters and Setters
    public Long getIdSimulacao() {
        return idSimulacao;
    }
    
    public void setIdSimulacao(Long idSimulacao) {
        this.idSimulacao = idSimulacao;
    }
    
    public Integer getCodigoProduto() {
        return codigoProduto;
    }
    
    public void setCodigoProduto(Integer codigoProduto) {
        this.codigoProduto = codigoProduto;
    }
    
    public BigDecimal getValorDesejado() {
        return valorDesejado;
    }
    
    public void setValorDesejado(BigDecimal valorDesejado) {
        this.valorDesejado = valorDesejado;
    }
    
    public Integer getPrazo() {
        return prazo;
    }
    
    public void setPrazo(Integer prazo) {
        this.prazo = prazo;
    }
    
    public BigDecimal getTaxaJuros() {
        return taxaJuros;
    }
    
    public void setTaxaJuros(BigDecimal taxaJuros) {
        this.taxaJuros = taxaJuros;
    }
    
    public BigDecimal getValorTotalParcelas() {
        return valorTotalParcelas;
    }
    
    public void setValorTotalParcelas(BigDecimal valorTotalParcelas) {
        this.valorTotalParcelas = valorTotalParcelas;
    }
    
    public LocalDateTime getDataSimulacao() {
        return dataSimulacao;
    }
    
    public void setDataSimulacao(LocalDateTime dataSimulacao) {
        this.dataSimulacao = dataSimulacao;
    }
    
    public Long getTempoRespostaMilissegundos() {
        return tempoRespostaMilissegundos;
    }
    
    public void setTempoRespostaMilissegundos(Long tempoRespostaMilissegundos) {
        this.tempoRespostaMilissegundos = tempoRespostaMilissegundos;
    }
}
