package br.unip.chatserver.model.clientactions.util;

import org.apache.commons.lang3.StringUtils;

import br.unip.chatserver.model.ClientConnection;
import br.unip.chatserver.model.Server;

public final class ActionsUtil {
	
	private ActionsUtil() {
		
	}
	
	public static ClientConnection retornaClienteComUsuarioInformado(String usuario) {
		for (ClientConnection cl : Server.getClientList()) {
			if (clientFoiEncontrado(usuario, cl)) {
				return cl;
			}
		}
		return null;
	}
	
	private static boolean clientFoiEncontrado(String destinatario, ClientConnection client) {
		return (client.getUser() != null) ? destinatario.equalsIgnoreCase(client.getUser().getLogin()) : false;
	}
	
	public static boolean validaToken(String[] tokens) {
		if (tokens.length != 3) {
			return false;
		}
		for (int i = 0; i < tokens.length; i++) {
			if (tokens[i].isEmpty() || StringUtils.isBlank(tokens[i])) {				
				return false;
			}
		}
		return true;
	}
}
