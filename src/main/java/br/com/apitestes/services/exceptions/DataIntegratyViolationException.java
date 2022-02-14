package br.com.apitestes.services.exceptions;

public class DataIntegratyViolationException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public DataIntegratyViolationException(String mensagem) {
		super(mensagem);
	}
}
