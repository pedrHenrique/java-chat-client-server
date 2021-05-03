package br.unip.chatserver.model.clientactions;

import static br.unip.chatserver.model.clientactions.ClientNotificationActions.exibeUsuariosOnlineParaCliente;
import static br.unip.chatserver.model.clientactions.ClientNotificationActions.notificaClientViaOutput;
import static br.unip.chatserver.model.clientactions.ClientNotificationActions.notificaTodosOsUsuarios;

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

	protected static boolean usuarioValido(Usuario usuario) {
		return (usuario.getLogin().equals("Felipe") && usuario.getPassword().equals("Felipe"))
				|| (usuario.getLogin().equalsIgnoreCase("Erick") && usuario.getPassword().equalsIgnoreCase("Erick")
				|| (usuario.getLogin().equalsIgnoreCase("Fernando")	&& usuario.getPassword().equalsIgnoreCase("Fernando"))
				|| (usuario.getLogin().equalsIgnoreCase("Karina") && usuario.getPassword().equalsIgnoreCase("Karina"))
				|| (usuario.getLogin().equalsIgnoreCase("Pedro") && usuario.getPassword().equalsIgnoreCase("Pedro"))
				|| (usuario.getLogin().equalsIgnoreCase("Gustavo") && usuario.getPassword().equalsIgnoreCase("Gustavo")));
	}

}
