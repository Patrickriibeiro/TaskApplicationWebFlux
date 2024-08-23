package br.com.patrickriibeiro.tasks.exception;

public class CepNotFoundException extends  RuntimeException {
    public CepNotFoundException(){
        super("Cep not Found.");
    }
}
