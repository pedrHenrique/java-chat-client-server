package br.unip.chatserver.model;

import java.io.IOException;
import java.util.List;

public class ClientActions {	
	
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
//
//	// format: "msg" "login" body...
//	// format: "msg" "#topic" body...
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
//
	protected static void handleLogoff(ClientConnection client) {
		if (isUserLogado(client)) {
			notificaUsuarios(client, client.getUser().getLogin() + " ficou offline.");
		}
		Server.removeClientConnection(client);
		finalizaClientSocket(client);
	}
	
	public static void finalizaClientSocket(ClientConnection client) {
		try {
			client.getClientSocket().close();
		} catch (IOException e) {
			System.err.println("Não foi possível fechar o soquete " + client.getClientSocket() + ".\nMotivo: " + e.getStackTrace());
		}
	}
	
	/**
	 * Notifica todos os clientes que estejam com algum usuário logado, alguma mensagem.<p>
	 * Não notifica o <b>remetente</b> da mensagem.
	 *
	 * @param remetente - O remetente da mensagem.
	 * @param mensagem - A mensagem.
	 */
	private static void notificaUsuarios(ClientConnection remetente, String mensagem) {
		for (ClientConnection client : Server.getClientList()) {
			if (!remetente.getUser().equals(client.getUser()) && isUserLogado(client)) {
				send(client, mensagem);
			}
		}
	}

	/**
	 * Notifica para o client informado, todos os outros usuários que estão online.
	 *
	 * @param client - O cliente que irá receber a lista de usuários online.
	 */
	private void exibeUsuariosOnlineParaUsuarioEspecifico(ClientConnection client) {
		for (ClientConnection cl : Server.getClientList()) {
			if (isUserLogado(cl) && !client.getUser().equals(cl.getUser())) {
				send(client, "Online " + cl.getUser().getLogin()+ "\n");
			}
		}
	}

	protected void handleLogin(ClientConnection client, String[] tokens) {
		Usuario usuario = this.validaLoginToken(client, tokens);
		//TODO validar se o usuário passado está sendo validado corretamente e se existe no banco
		if (usuario != null && loginbiarra(usuario)) { // Exemplo se a validação ter sido feita com sucesso.
			client.setUser(usuario);
			Server.notificaNoConsoleDoServidor("Login do usuário: " + usuario.toString());
			exibeUsuariosOnlineParaUsuarioEspecifico(client);
			notificaUsuarios(client, "Online " + usuario.getLogin());
		} else {			
			Server.notificaNoConsoleDoServidor("Cliente: " + client + " teve uma tentativa de login falha!");
			notificaClientViaOutput(client, "Usuário ou senha informados não estão corretos.");
		}
	}

	private Usuario validaLoginToken(ClientConnection client, String[] tokens) {
		String login;
		String password;
		try {
			login = tokens[1];
			password = tokens[2];
			return new Usuario(null, login, password);
		} catch (Exception e) {
			notificaClientViaOutput(client, "Ops. O login ou a senha não foi foram informados corretamente. Tente novamente!\n");
			return null;
		}
	}

	private boolean loginbiarra(Usuario usuario) {
		return 	(usuario.getLogin().equals("Felipe") && usuario.getPassword().equals("Felipe"))
				|| (usuario.getLogin().equalsIgnoreCase("Erick") && usuario.getPassword().equalsIgnoreCase("Eric")
				|| (usuario.getLogin().equalsIgnoreCase("Fernando") && usuario.getPassword().equalsIgnoreCase("Fernando"))
				|| (usuario.getLogin().equalsIgnoreCase("Karina") && usuario.getPassword().equalsIgnoreCase("Karina"))
				|| (usuario.getLogin().equalsIgnoreCase("Pedro") && usuario.getPassword().equalsIgnoreCase("Pedro"))
				|| (usuario.getLogin().equalsIgnoreCase("Gustavo") && usuario.getPassword().equalsIgnoreCase("Gustavo")));
	}

	// TODO precisa ser olhado a fundo!!
	private static void send(ClientConnection client, String msg) {
		if (isUserLogado(client)) {
			try {
				notificaClientViaOutput(client, msg);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			notificaClientViaOutput(client, "Você precisa estar logado para enviar alguma mensagem!");
		}
	}

	protected static void notificaClientViaOutput(ClientConnection client, String mensagem) {
		try {
			if (client.getOutputStream() == null) {
				client.setOutputStream(client.getOutputStream());
			}
			client.getOutputStream().write(mensagem.getBytes());
		} catch (Exception e) {
			System.err.print("Não foi possível notificar pelo output o client " + client.getOutputStream() + ".\nMotivo: "
					+ e.getStackTrace());
		}
	}
	
	private static boolean isUserLogado(ClientConnection client) {
		return client.getUser() != null;
	}
}
