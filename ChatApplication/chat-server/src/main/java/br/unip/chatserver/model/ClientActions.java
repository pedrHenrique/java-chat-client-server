package br.unip.chatserver.model;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;

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
	// FIXME - Aparentemente, o primeiro usuario, ao logar, e tentar conversar com o
	// outro usuário que acabou de logar depois dele. O primeiro usuário não
	// consegue enviar uma mensagem
	// FIXME - Correções gerais na classe.
	public void handleMessage(ClientConnection clientRemetente, String linha) {
		if (!clientRemetente.isUserLogado()) {
			notificaClientViaOutput(clientRemetente, "Você precisa estar logado para enviar alguma mensagem.\n");
			return;
		}		
		String[] tokens = this.validaTokenMensagem(linha);		
		if (tokens != null) {
			String destinatario = tokens[1];
			String mensagem = tokens[2];
			this.notificaUsuario(clientRemetente, destinatario, mensagem);
		} else {
			notificaClientViaOutput(clientRemetente, "Ops, a forma como você deseja enviar uma mensagem não pode ser aceita.\n");
		}
	}

	private void notificaUsuario(ClientConnection clientRemetente, String destinatario, String mensagem) {
		if (clientRemetente.getUser().getLogin().equalsIgnoreCase(destinatario)) {
			notificaClientViaOutput(clientRemetente, "Porquê você estaria tentando enviar uma mensagem para você mesmo?\n");
			return;
		}
		ClientConnection clientDestinatario = retornaClienteComUsuarioInformado(destinatario);
		if (destinatario != null) {				
			mensagem = formataMensagemChat(clientRemetente, mensagem);
			enviaMensagem(clientRemetente, mensagem , clientDestinatario);
			return;
		}
		notificaClientViaOutput(clientRemetente, "Não foi possível encontrar o usuário " + destinatario + ".\n");
	}
		
			
	
	public ClientConnection retornaClienteComUsuarioInformado(String usuario) {
		for (ClientConnection cl : Server.getClientList()) {
			if (clientFoiEncontrado(usuario, cl)) {
				return cl;
			}
		}
		return null;
	}
	
	private boolean clientFoiEncontrado(String destinatario, ClientConnection client) {
		return destinatario.equalsIgnoreCase(client.getUser().getLogin());
	}
	
	private String formataMensagemChat(ClientConnection remetente, String mensagem) {
		return remetente.getUser().getLogin() + ": " + mensagem + "\n";
	}

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
			System.err.println("Não foi possível fechar o soquete " + client.getClientSocket() + ".\nMotivo: " + e.getStackTrace());
		}
	}

	protected void handleLogin(ClientConnection client, String[] tokens) {
		boolean isTokenValidado = this.validaToken(tokens);
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
		} else {
			notificaClientViaOutput(client, "Ops, a forma como a senha ou o login foram informados não pode ser aceita.\n");
		}
	}

	private boolean validaToken(String[] tokens) {
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

	private String[] validaTokenMensagem(String linha) {
		String[] tokens = formataTokenMensagem(linha);
		return (validaToken(tokens)) ? tokens : null; //Retorna null se os tokens não forem válidos	
	}
	
	// retorna a seguinte 'array': msg, destinatario, 'Uma mensagem completa, podendo conter espaços e outras paradas'
	private String[] formataTokenMensagem(String linha) {
		return StringUtils.split(linha, null, 3);
	}

	// TODO validar se o usuário passado está sendo validado corretamente e se
	// existe no banco
	// Esta validação é apenas para teste
	private boolean usuarioValido(Usuario usuario) {
		return (usuario.getLogin().equals("Felipe") && usuario.getPassword().equals("Felipe"))
				|| (usuario.getLogin().equalsIgnoreCase("Erick") && usuario.getPassword().equalsIgnoreCase("Erick")
				|| (usuario.getLogin().equalsIgnoreCase("Fernando")	&& usuario.getPassword().equalsIgnoreCase("Fernando"))
				|| (usuario.getLogin().equalsIgnoreCase("Karina") && usuario.getPassword().equalsIgnoreCase("Karina"))
				|| (usuario.getLogin().equalsIgnoreCase("Pedro") && usuario.getPassword().equalsIgnoreCase("Pedro"))
				|| (usuario.getLogin().equalsIgnoreCase("Gustavo") && usuario.getPassword().equalsIgnoreCase("Gustavo")));
	}

	protected static boolean isUserLogado(ClientConnection client) {
		return client.getUser() != null;
	}

}
