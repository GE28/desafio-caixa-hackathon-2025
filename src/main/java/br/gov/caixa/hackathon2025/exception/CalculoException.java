package br.gov.caixa.hackathon2025.exception;

/** Exceção lançada quando ocorre erro nos cálculos de empréstimo. */
public class CalculoException extends BaseException {
    
    private static final long serialVersionUID = 1L;
    
    public CalculoException(String mensagem) {
        super(mensagem);
    }
    
    public CalculoException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
