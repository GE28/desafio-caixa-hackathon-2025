package br.gov.caixa.hackathon2025.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.jboss.logging.Logger;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

/** Handler para exceções de validação do Bean Validation */
@Provider
public class ValidationExceptionHandler implements ExceptionMapper<ConstraintViolationException> {
    
    private static final Logger logger = Logger.getLogger(ValidationExceptionHandler.class);

    @Context
    UriInfo uriInfo;

    @Override
    public Response toResponse(ConstraintViolationException exception) {
        String path = uriInfo != null ? uriInfo.getPath() : "";
        
        String mensagem = exception.getConstraintViolations()
                .stream()
                .map(this::formatConstraintViolation)
                .collect(Collectors.joining("; "));
        
        logger.warnf("Erro de validação: %s", mensagem);
        
        ErroResponse erro = new ErroResponse(
            Response.Status.BAD_REQUEST.getStatusCode(),
            "Dados de entrada inválidos: " + mensagem,
            LocalDateTime.now(),
            path
        );
        
        return Response.status(Response.Status.BAD_REQUEST).entity(erro).build();
    }
    
    private String formatConstraintViolation(ConstraintViolation<?> violation) {
        String propertyPath = violation.getPropertyPath().toString();
        String message = violation.getMessage();
        return String.format("%s: %s", propertyPath, message);
    }
}
