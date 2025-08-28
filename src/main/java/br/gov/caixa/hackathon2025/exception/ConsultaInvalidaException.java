package br.gov.caixa.hackathon2025.exception;

/** Exceção lançada quando parâmetros de consulta são inválidos. */
public class ConsultaInvalidaException extends BaseException {
    
    private static final long serialVersionUID = 1L;
    
    public ConsultaInvalidaException(String mensagem) {
        super(mensagem);
    }
    
    public ConsultaInvalidaException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
