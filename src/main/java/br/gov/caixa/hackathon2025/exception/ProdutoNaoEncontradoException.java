package br.gov.caixa.hackathon2025.exception;

/** Exceção lançada quando um produto não é encontrado. */
public class ProdutoNaoEncontradoException extends BaseException {
    
    private static final long serialVersionUID = 1L;
    
    public ProdutoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
    
    public ProdutoNaoEncontradoException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
