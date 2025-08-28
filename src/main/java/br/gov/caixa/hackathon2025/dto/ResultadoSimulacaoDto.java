package br.gov.caixa.hackathon2025.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import java.util.List;

@Schema(description = "Resultado da simulação por tipo de amortização")
public class ResultadoSimulacaoDto {
    
    @JsonProperty("tipo")
    @Schema(description = "Tipo de amortização", example = "SAC")
    private String tipo;
    
    @JsonProperty("parcelas")
    @Schema(description = "Lista de parcelas calculadas")
    private List<ParcelaDto> parcelas;
    
    // Constructors
    public ResultadoSimulacaoDto() {}
    
    public ResultadoSimulacaoDto(String tipo, List<ParcelaDto> parcelas) {
        this.tipo = tipo;
        this.parcelas = parcelas;
    }
    
    // Getters and Setters
    public String getTipo() {
        return tipo;
    }
    
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    public List<ParcelaDto> getParcelas() {
        return parcelas;
    }
    
    public void setParcelas(List<ParcelaDto> parcelas) {
        this.parcelas = parcelas;
    }
}
