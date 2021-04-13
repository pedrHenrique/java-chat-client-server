package br.unip.chatserver.model;

import java.io.IOException;
import java.util.List;

public class ClientActions extends ClientNotificationActions {

	protected ClientActions() {

	}

//	public void handleLeave(String[] tokens) {
//		if (tokens.length > 1) {
//			String topic = tokens[1];
//			topicSet.remove(topic);
//		}
//	}
//
//	private boolean isMemberOfTopic(String topic) {
//		return topicSet.contains(topic);
//	}
//
//	private void handleJoin(String[] tokens) {
//		if (tokens.length > 1) {
//			String topic = tokens[1];
//			topicSet.add(topic);
//		}
//	}

	
	// format: "msg" "login" body...
	// format: "msg" "#topic" body...
//	private void handleMessage(String[] tokens) throws IOException {
//		String sendTo = tokens[1];
//		String body = tokens[2];
//
//		boolean isTopic = sendTo.charAt(0) == '#';
//
//		List<ClientConnection> workerList = Server.getClientList();
//		for (ClientConnection worker : workerList) {
//			if (isTopic) {
//				if (worker.isMemberOfTopic(sendTo)) {
//					String outMsg = "msg " + sendTo + ":" + user.getLogin() + " " + body + "\n";
//					this.send(this, outMsg);
//				}
//			} else {
//				if (sendTo.equalsIgnoreCase(worker.getUser().getLogin())) {
//					String outMsg = "msg " + user.getLogin() + " " + body + "\n";
//					this.send(this, outMsg);
//				}
//			}
//		}
//	}

	protected static void handleLogoff(ClientConnection client) {
		if (isUserLogado(client)) {
			notificaTodosOsUsuarios(client, client.getUser().getLogin() + " ficou offline.\n");
		}
		Server.removeClientConnection(client);
		finalizaClientSocket(client);
	}

	public static void finalizaClientSocket(ClientConnection client) {
		try {
			client.getClientSocket().close();
		} catch (IOException e) {
			System.err.println("Não foi possível fechar o soquete " + client.getClientSocket() + ".\nMotivo: "
					+ e.getStackTrace());
		}
	}

	protected void handleLogin(ClientConnection client, String[] tokens) {
		boolean isTokenValidado = this.validaTokenLogin(client, tokens);
		// TODO -> Validar Usuário com Banco.
		if (isTokenValidado) {
			String login = tokens[1];
			String senha = tokens[2];
			Usuario usuario = new Usuario(null, login, senha);
			if (usuarioValido(usuario)) {
				client.setUser(usuario);
				notificaClientViaOutput(client, "Login realizado com sucesso!\n");
				exibeUsuariosOnlineParaCliente(client);
				notificaTodosOsUsuarios(client, client.getUser().getLogin() + " ficou online.\n");
				Server.notificaNoConsoleDoServidor("Login do usuário: " + usuario.toString());
			} else {
				Server.notificaNoConsoleDoServidor("Cliente: " + client.getClientSocket() + " teve uma tentativa de login falha!");
				notificaClientViaOutput(client, "Usuário ou senha informados não correspondem!\n");
			}
		}
	}

	private boolean validaTokenLogin(ClientConnection client, String[] tokens) {
		// validando se o login e a senha foram informados corretamente
		if (tokens.length != 3) {
			notificaClientViaOutput(client, "Por favor, informe um usuário e uma senha.\n");
			return false;
		}
		for (int i = 0; i < tokens.length; i++) {
			if (tokens[i].isEmpty()) {
				notificaClientViaOutput(client,"Ops, a forma como a senha ou o login foram informados não pode ser aceita.\n");
				return false;
			}
		}
		return true;
	}

	// TODO validar se o usuário passado está sendo validado corretamente e se existe no banco
	// Esta validação é apenas para teste
	private boolean usuarioValido(Usuario usuario) {
		return (usuario.getLogin().equals("Felipe") && usuario.getPassword().equals("Felipe"))
				|| (usuario.getLogin().equalsIgnoreCase("Erick") 	&& usuario.getPassword().equalsIgnoreCase("Erick")
				|| (usuario.getLogin().equalsIgnoreCase("Fernando") && usuario.getPassword().equalsIgnoreCase("Fernando"))
				|| (usuario.getLogin().equalsIgnoreCase("Karina")	&& usuario.getPassword().equalsIgnoreCase("Karina"))
				|| (usuario.getLogin().equalsIgnoreCase("Pedro")	&& usuario.getPassword().equalsIgnoreCase("Pedro"))
				|| (usuario.getLogin().equalsIgnoreCase("Gustavo")	&& usuario.getPassword().equalsIgnoreCase("Gustavo")));
	}	
	
	protected static boolean isUserLogado(ClientConnection client) {
		return client.getUser() != null;
	}
	
}
