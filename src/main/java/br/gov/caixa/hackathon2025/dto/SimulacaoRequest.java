package br.gov.caixa.hackathon2025.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import java.math.BigDecimal;

@Schema(description = "Dados para simulação de empréstimo")
public class SimulacaoRequest {
    
    @JsonProperty("valorDesejado")
    @NotNull(message = "Valor desejado é obrigatório")
    @Positive(message = "Valor desejado deve ser positivo")
    @Schema(description = "Valor desejado para o empréstimo", example = "1000.00")
    private BigDecimal valorDesejado;
    
    @JsonProperty("prazo")
    @NotNull(message = "Prazo é obrigatório")
    @Positive(message = "Prazo deve ser positivo")
    @Schema(description = "Prazo em meses", example = "24")
    private Integer prazo;
    
    // Constructors
    public SimulacaoRequest() {}
    
    public SimulacaoRequest(BigDecimal valorDesejado, Integer prazo) {
        this.valorDesejado = valorDesejado;
        this.prazo = prazo;
    }
    
    // Getters and Setters
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
}
