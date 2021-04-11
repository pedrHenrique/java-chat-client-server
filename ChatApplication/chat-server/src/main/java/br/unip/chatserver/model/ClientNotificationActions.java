package br.unip.chatserver.model;

import static br.unip.chatserver.model.ClientActions.isUserLogado;

import java.io.IOException;

public class ClientNotificationActions {
	
	/**
	 * Notifica todos os clientes que estejam com algum usuário logado, alguma mensagem.<p>
	 * 
	 * Não notifica o <b>remetente</b> da mensagem.
	 *
	 * @param remetente - O remetente da mensagem.
	 * @param mensagem  - A mensagem.
	 */
	protected static void notificaTodosOsUsuarios(ClientConnection remetente, String mensagem) {
		for (ClientConnection client : Server.getClientList()) {
			if (clienteNaoForORemetente(remetente, client) && isUserLogado(client)) {
				enviaMensagem(client, mensagem);
			}
		}
	}

	private static boolean clienteNaoForORemetente(ClientConnection remetente, ClientConnection client) {
		return !remetente.getUser().equals(client.getUser());
	}

	/**
	 * Notifica para o client informado, todos os outros clientes que estejam com algum usuário online.
	 *
	 * @param client - O cliente que irá receber a lista de usuários online.
	 */
	protected static void exibeUsuariosOnlineParaCliente(ClientConnection client) {
		notificaClientViaOutput(client, "Lista de usuários online.\n");
		for (ClientConnection cl : Server.getClientList()) {
			if (clienteNaoForORemetente(client, cl) && isUserLogado(cl)) {
				enviaMensagem(client, cl.getUser().getLogin() + "\n");
			}
		}
	}	

	/**
	 * Envia uma mensagem para o console do cliente.<p>
	 * 
	 * Neste caso, o mesmo <b>não precisa estar com um usuário logado</b> para poder visualizar a mensagem.
	 * 
	 * @param client - O Cliente que receberá a mensagem.
	 * @param mensagem - A mensagem.
	 */
	protected static void notificaClientViaOutput(ClientConnection client, String mensagem) {
		try {
			client.getOutputStream().write(mensagem.getBytes());
		} catch (IOException e) {
			System.err.print("Não foi possível notificar pelo output o client " + client + ".\nMotivo: " + e.getStackTrace());
		}
	}
	
	/**
	 * Envia uma mensagem para o console do cliente.<p>
	 * 
	 * Neste caso, a mensagem só será enviada se o <b>cliente estiver com um usuário logado no momento<b>.
	 * 
	 * @param client - O Cliente que receberá a mensagem.
	 * @param mensagem - A mensagem.
	 */
	protected static void enviaMensagem(ClientConnection client, String mensagem) {
		if (isUserLogado(client)) {
			notificaClientViaOutput(client, mensagem);
			return;
		}
		notificaClientViaOutput(client, "Você precisa estar logado para enviar alguma mensagem!");
	}

}
