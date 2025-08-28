package br.gov.caixa.hackathon2025.exception;

/** Exceção lançada quando dados de entrada são inválidos. */
public class EntradaInvalidaException extends BaseException {
    
    private static final long serialVersionUID = 1L;
    
    public EntradaInvalidaException(String mensagem) {
        super(mensagem);
    }
    
    public EntradaInvalidaException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
