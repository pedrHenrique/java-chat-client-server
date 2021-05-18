package br.unip.chatserver.clientactions;

import static br.unip.chatserver.clientactions.ClientNotificationActions.exibeUsuariosOnlineParaCliente;
import static br.unip.chatserver.clientactions.ClientNotificationActions.notificaClientViaOutput;
import static br.unip.chatserver.clientactions.ClientNotificationActions.notificaTodosOsUsuarios;

import br.unip.chatserver.model.ClientConnection;
import br.unip.chatserver.model.Server;
import br.unip.chatserver.model.Usuario;

public final class ClientLoginActions {
	
	private ClientLoginActions() {
		
	}
	
	protected static void notificacoesPosClienteLogado(ClientConnection client, Usuario usuario) {
		notificaClientViaOutput(client, "Login realizado com sucesso!\n");
		//exibeUsuariosOnlineParaCliente(client);
		notificaTodosOsUsuarios(client, "online " + client.getUser().getLogin() + "\n");
		Server.notificaNoConsoleDoServidor("Login do usuário: " + usuario.toString());
	}

}
