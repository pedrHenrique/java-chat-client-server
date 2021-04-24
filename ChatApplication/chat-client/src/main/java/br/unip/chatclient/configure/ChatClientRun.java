package br.unip.chatclient.configure;

import java.io.IOException;

import br.unip.chatclient.model.ServerConnection;
import br.unip.chatclient.util.notifier.UserMessageNotifier;
import br.unip.chatclient.view.Login;

public class ChatClientRun {

	private static final String SERVERADRESS = "localhost";

	private static final Integer SERVERPORT = 8818;

	public static void main(String[] args) {
		Login login = new Login();
		ServerConnection conexao = null;
		try {
			conexao = new ServerConnection(SERVERADRESS, SERVERPORT);
			conexao.connect();
		} catch (IOException e) {
			UserMessageNotifier.errorMessagePane(login, conexao.getConnectionStatusMessage());
			System.exit(1);
		}
	}

}
