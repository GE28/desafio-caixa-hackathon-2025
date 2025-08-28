package br.gov.caixa.hackathon2025.exception;

/** Exceção lançada quando uma simulação não é encontrada. */
public class SimulacaoNaoEncontradaException extends BaseException {
    
    private static final long serialVersionUID = 1L;
    
    public SimulacaoNaoEncontradaException(String mensagem) {
        super(mensagem);
    }
    
    public SimulacaoNaoEncontradaException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
