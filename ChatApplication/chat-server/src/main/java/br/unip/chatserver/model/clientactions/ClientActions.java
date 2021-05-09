package br.unip.chatserver.model.clientactions;

import static br.unip.chatserver.model.clientactions.ClientNotificationActions.exibeUsuariosOnlineParaCliente;
import static br.unip.chatserver.model.clientactions.ClientNotificationActions.notificaClientViaOutput;
import static br.unip.chatserver.model.clientactions.ClientNotificationActions.notificaTodosOsUsuarios;
import static br.unip.chatserver.model.clientactions.ClientMessageActions.validaTokenMensagem;
import static br.unip.chatserver.model.clientactions.ClientMessageActions.notificaUsuario;
import static br.unip.chatserver.model.clientactions.util.ActionsUtil.validaToken;
import static br.unip.chatserver.model.clientactions.ClientLoginActions.usuarioValido;
import static br.unip.chatserver.model.clientactions.ClientLoginActions.notificacoesPosClienteLogado;

import java.io.IOException;

import br.unip.chatserver.model.ClientConnection;
import br.unip.chatserver.model.Server;
import br.unip.chatserver.model.Usuario;

public class ClientActions {

	protected ClientActions() {

	}

	public static void handleLogoff(ClientConnection client) {
		if (client.isUserLogado()) {
			notificaTodosOsUsuarios(client, "offline " + client.getUser().getLogin() + "\n");
		}
		finalizaClientSocket(client);
	}

	public static void finalizaClientSocket(ClientConnection client) {
		try {
			client.getClientSocket().close();
			Server.removeClientConnection(client);
			Server.notificaNoConsoleDoServidor(client + " se desconectou do servidor.");
		} catch (IOException e) {
			System.err.println("Não foi possível fechar o soquete " + client.getClientSocket() + ".\nMotivo: " + e.getStackTrace());
			notificaClientViaOutput(client, "falha: Não foi possível fechar sua conexão com o servidor... Por favor, tente novamente uma outra vez.\n");
		}
	}
	
	public static Usuario showUser(ClientConnection client) {
		if (client.isUserLogado()) {
			notificaClientViaOutput(client, client.getUser() + "\n");
			return client.getUser();
		}
		notificaClientViaOutput(client, "Falha. Você não está logado no momento!\n");
		return null;
	}
	
	public static void showOnlineUserList(ClientConnection client) {
		exibeUsuariosOnlineParaCliente(client);		
	}

	public static void handleLogin(ClientConnection client, String[] tokens) {
		boolean isTokenValidado = validaToken(tokens);
		// TODO -> Validar Usuário com Banco.
		if (isTokenValidado) {
			String login = tokens[1];
			String senha = tokens[2];			
			Usuario usuario = new Usuario(null, login, senha);
			if (usuarioValido(usuario)) {
				client.setUser(usuario);
				notificacoesPosClienteLogado(client, usuario);
			} else {
				Server.notificaNoConsoleDoServidor("Cliente: " + client.getClientSocket() + " teve uma tentativa de login falha!");
				notificaClientViaOutput(client, "Usuário ou senha informados não correspondem!\n");
			}
		} else {
			notificaClientViaOutput(client, "Ops, a forma como a senha ou o login foram informados não pode ser aceita.\n");
		}
		
	}

	public static void handleMessage(ClientConnection clientRemetente, String linha) {
		if (!clientRemetente.isUserLogado()) {
			notificaClientViaOutput(clientRemetente, "Você precisa estar logado para enviar alguma mensagem.\n");
			return;
		}
		String[] tokens = validaTokenMensagem(linha);		
		if (tokens != null) {
			String destinatario = tokens[1];
			String mensagem = tokens[2];
			notificaUsuario(clientRemetente, destinatario, mensagem);
		} else {
			notificaClientViaOutput(clientRemetente, "Falha, a forma como você deseja enviar uma mensagem não pode ser aceita.\n");
		}
	}

}
