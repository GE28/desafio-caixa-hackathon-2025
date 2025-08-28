package br.gov.caixa.hackathon2025.exception;

import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.jboss.logging.Logger;

import java.time.LocalDateTime;

/** Handler global para tratamento de exceções customizadas */
@Provider
public class GlobalExceptionHandler implements ExceptionMapper<RuntimeException> {
    
    private static final Logger logger = Logger.getLogger(GlobalExceptionHandler.class);

    @Context
    UriInfo uriInfo;

    @Override
    public Response toResponse(RuntimeException exception) {
        String path = uriInfo != null ? uriInfo.getPath() : "";
        
        if (exception instanceof BaseException) {
            return handleBaseException((BaseException) exception, path);
        }
        
        return handleErroGenerico(exception, path);
    }

    private Response handleBaseException(BaseException ex, String path) {
        int statusCode = determinarStatusCode(ex);
        
        if (statusCode >= 500) {
            logger.errorf("[%s]: %s", ex.getTipoExcecao(), ex.getMessage());
        } else {
            logger.warnf("[%s]: %s", ex.getTipoExcecao(), ex.getMessage());
        }
        
        ErroResponse erro = new ErroResponse(
            statusCode,
            ex.getMessage(),
            LocalDateTime.now(),
            path
        );
        
        return Response.status(statusCode).entity(erro).build();
    }
    
    private int determinarStatusCode(BaseException ex) {
        return switch (ex.getClass().getSimpleName()) {
            case "EntradaInvalidaException", "ConsultaInvalidaException" -> 
                Response.Status.BAD_REQUEST.getStatusCode();
            case "ProdutoNaoEncontradoException", "SimulacaoNaoEncontradaException" -> 
                Response.Status.NOT_FOUND.getStatusCode();
            case "CalculoException" -> 
                422; // Unprocessable Entity
            default -> 
                Response.Status.INTERNAL_SERVER_ERROR.getStatusCode();
        };
    }

    private Response handleErroGenerico(RuntimeException ex, String path) {
        logger.errorf(ex, "Erro inesperado: %s", ex.getMessage());
        ErroResponse erro = new ErroResponse(
            Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
            "Erro interno no servidor. Tente novamente mais tarde.",
            LocalDateTime.now(),
            path
        );
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(erro).build();
    }
}
