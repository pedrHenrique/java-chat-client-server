package br.unip.chatclient.configure;

import java.io.IOException;

import br.unip.chatclient.model.ServerCommunication;
import br.unip.chatclient.model.ServerConnection;
import br.unip.chatclient.util.notifier.UserMessageNotifier;
import br.unip.chatclient.view.Login;

public class ChatClientRun {

	private static final String SERVERADRESS = "localhost";

	private static final Integer SERVERPORT = 8818;

	public static void main(String[] args) {
		Login login = new Login();
		try {
			ServerConnection serverConnection = new ServerConnection(SERVERADRESS, SERVERPORT);
			serverConnection.connect();
			ServerCommunication comunicador = serverConnection.retornaComunicadorComServidor(serverConnection);
			login.setServerCommunicator(comunicador);
		} catch (IOException e) {
			UserMessageNotifier.errorMessagePane(login, e.getMessage());
			System.exit(1);
		}
	}

}
