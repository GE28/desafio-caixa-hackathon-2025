package br.gov.caixa.hackathon2025.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import br.gov.caixa.hackathon2025.dto.ListaSimulacaoResponse;
import br.gov.caixa.hackathon2025.dto.TelemetriaResponse;
import br.gov.caixa.hackathon2025.dto.VolumeSimulacaoResponse;
import br.gov.caixa.hackathon2025.exception.ConsultaInvalidaException;
import br.gov.caixa.hackathon2025.service.RelatorioService;
import br.gov.caixa.hackathon2025.service.SimulacaoService;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@Path("/relatorios")
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "Relatórios", description = "Operações de relatórios e consultas")
public class RelatorioResource {
    private final SimulacaoService simulacaoService;
    private final RelatorioService relatorioService;
    
    @Inject
    public RelatorioResource(SimulacaoService simulacaoService, RelatorioService relatorioService) {
        this.simulacaoService = simulacaoService;
        this.relatorioService = relatorioService;
    }
    
    @GET
    @Path("/simulacoes")
    @Operation(
        summary = "Listar simulações (relatório)",
        description = "Lista todas as simulações realizadas com paginação para relatórios"
    )
    public Response listarSimulacoes(
            @Parameter(description = "Número da página", example = "1")
            @QueryParam("pagina") Integer pagina,
            @Parameter(description = "Tamanho da página", example = "50")
            @QueryParam("tamanho") Integer tamanho) {
        
        ListaSimulacaoResponse response = simulacaoService.listarSimulacoes(pagina, tamanho);
        return Response.ok(response).build();
    }
    
    @GET
    @Path("/volume/{data}")
    @Operation(
        summary = "Volume de simulações por data",
        description = "Obtém o volume de simulações agrupado por produto para uma data específica"
    )
    public Response obterVolumePorData(
            @Parameter(description = "Data no formato YYYY-MM-DD", example = "2025-01-15", required = true)
            @PathParam("data") String dataStr) {
        LocalDate data = validarEConverterData(dataStr);
        VolumeSimulacaoResponse response = relatorioService.obterVolumeSimulacaoPorData(data);
        return Response.ok(response).build();
    }
    
    @GET
    @Path("/telemetria/{data}")
    @Operation(
        summary = "Telemetria da API por data",
        description = "Obtém métricas de performance e telemetria dos endpoints para uma data específica"
    )
    public Response obterTelemetria(
            @Parameter(description = "Data no formato YYYY-MM-DD", example = "2025-01-15", required = true)
            @PathParam("data") String dataStr) {
        LocalDate data = validarEConverterData(dataStr);
        TelemetriaResponse response = relatorioService.obterTelemetria(data);
        return Response.ok(response).build();
    }
    
    /**
     * Método utilitário para validar e converter string de data
     */
    private LocalDate validarEConverterData(String dataStr) {
        if (dataStr == null || dataStr.trim().isEmpty()) {
            throw new ConsultaInvalidaException("Data não pode ser vazia");
        }
        
        try {
            return LocalDate.parse(dataStr.trim());
        } catch (DateTimeParseException e) {
            throw new ConsultaInvalidaException(
                String.format("Data inválida '%s'. Use o formato YYYY-MM-DD (exemplo: 2025-08-27)", dataStr),
                e
            );
        }
    }
}
