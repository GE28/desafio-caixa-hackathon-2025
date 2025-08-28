package br.gov.caixa.hackathon2025.resource;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.ExampleObject;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import br.gov.caixa.hackathon2025.dto.ListaSimulacaoResponse;
import br.gov.caixa.hackathon2025.dto.SimulacaoRequest;
import br.gov.caixa.hackathon2025.dto.SimulacaoResponse;
import br.gov.caixa.hackathon2025.service.SimulacaoService;

@Path("/simulacao")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Simulação", description = "Operações relacionadas à simulação de empréstimos")
public class SimulacaoResource {
    private final SimulacaoService simulacaoService;
    
    @Inject
    public SimulacaoResource(SimulacaoService simulacaoService) {
        this.simulacaoService = simulacaoService;
    }
    
    @POST
    @Operation(
        summary = "Criar nova simulação de empréstimo",
        description = "Realiza uma nova simulação de empréstimo calculando as parcelas para SAC e PRICE"
    )
    public Response simular(
            @Parameter(
                description = "Dados para simulação", 
                required = true, 
                content = @Content(
                    schema = @Schema(implementation = SimulacaoRequest.class),
                    examples = @ExampleObject(
                        name = "Exemplo básico",
                        value = "{\"valorDesejado\": 50000.00, \"prazo\": 24}"
                    )
                )
            )
            @Valid SimulacaoRequest request) {
        SimulacaoResponse response = simulacaoService.simularEmprestimo(request);
        return Response.ok(response).build();
    }

    @GET
    @Operation(
        summary = "Listar simulações",
        description = "Lista todas as simulações realizadas com paginação"
    )
    public Response listar(
        @Parameter(description = "Número da página", example = "1")
        @QueryParam("pagina") @DefaultValue("1") Integer pagina,
        
        @Parameter(description = "Tamanho da página", example = "10")
        @QueryParam("tamanho") @DefaultValue("200") Integer tamanho) {
        ListaSimulacaoResponse response = simulacaoService.listarSimulacoes(pagina, tamanho);
        return Response.ok(response).build();
    }
}
