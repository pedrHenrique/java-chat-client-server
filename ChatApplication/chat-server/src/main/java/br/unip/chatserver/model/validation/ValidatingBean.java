package br.unip.chatserver.model.validation;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;

import br.unip.chatserver.constants.ServerCommands;

public final class ValidatingBean {

	private static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

	public static void validaObject(Object classe) throws ConstraintViolationException {
		Set<ConstraintViolation<Object>> infracoes = validator.validate(classe);
		if (!infracoes.isEmpty()) {
			throw new ConstraintViolationException(retornaViolacoes(infracoes), infracoes);
		}
	}

	public static String retornaViolacoes(Set<ConstraintViolation<Object>> infracoes) {
		StringBuilder violacoes = new StringBuilder();
		for (ConstraintViolation<Object> error : infracoes) {
			violacoes.append(error.getMessage());
			break; // FIXME Não podemos enviar todas as infrações de uma vez.... Para a mensagem
					// ficar formatada, seria necessário informar um \n. É \n são considerados envio
					// de mensagem para o servidor
		}
		return violacoes.toString() + ServerCommands.ENTER_COMMAND;
	}

	private ValidatingBean() {

	}

}
