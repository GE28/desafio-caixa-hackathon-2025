package br.gov.caixa.hackathon2025.resource;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import br.gov.caixa.hackathon2025.dto.ListaSimulacaoResponse;
import br.gov.caixa.hackathon2025.dto.SimulacaoRequest;
import br.gov.caixa.hackathon2025.dto.SimulacaoResponse;
import br.gov.caixa.hackathon2025.service.SimulacaoService;

@Path("/simulacao")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SimulacaoResource {
    private final SimulacaoService simulacaoService;
    
    @Inject
    public SimulacaoResource(SimulacaoService simulacaoService) {
        this.simulacaoService = simulacaoService;
    }
    
    @POST
    public Response simular(@Valid SimulacaoRequest request) {
        try {
            SimulacaoResponse response = simulacaoService.simularEmprestimo(request);
            return Response.ok(response).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND)
                .entity("{\"erro\": \"" + e.getMessage() + "\"}")
                .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("{\"erro\": \"Erro interno do servidor\"}")
                .build();
        }
    }

    @GET
    public Response listar(
        @QueryParam("pagina") @DefaultValue("1") Integer pagina,
        @QueryParam("tamanho") @DefaultValue("200") Integer tamanho) {
        try {
            ListaSimulacaoResponse response = simulacaoService.listarSimulacoes(pagina, tamanho);
            return Response.ok(response).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("{\"erro\": \"Erro interno do servidor\"}")
                .build();
        }
    }
}
