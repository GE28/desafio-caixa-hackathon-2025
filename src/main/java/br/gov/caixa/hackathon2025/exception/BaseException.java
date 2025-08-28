package br.gov.caixa.hackathon2025.exception;

/**
 * Classe base para todas as exceções customizadas do sistema.
 * Fornece uma hierarquia comum e funcionalidades básicas para tratamento de erros.
 */
public abstract class BaseException extends RuntimeException {
    
    private static final long serialVersionUID = 1L;
    
    protected BaseException(String mensagem) {
        super(mensagem);
    }
    
    protected BaseException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
    
    /**
     * Retorna o tipo específico da exceção para logging e debugging
     */
    public String getTipoExcecao() {
        return this.getClass().getSimpleName();
    }
}