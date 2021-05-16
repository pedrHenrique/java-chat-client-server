package br.unip.chatserver.controler;

import static br.unip.chatserver.clientactions.ClientNotificationActions.notificaClientViaOutput;
import static br.unip.chatserver.constants.ServerCommands.*;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;

import br.unip.chatserver.clientactions.ClientActions;
import br.unip.chatserver.model.ClientConnection;
import br.unip.chatserver.model.Server;

public final class ClientActionHandler extends ClientActions {
	
	private final ClientConnection client;

	public ClientActionHandler(ClientConnection client) {
		this.client = client;
	}
	
	public void clientListener() {
		try {
			String line = client.getBufferedReader().readLine();
			String[] tokens = (line != null) ? StringUtils.split(line) : null;
			if (tokens != null && tokens.length > 0) {
				Server.notificaNoConsoleDoServidor(client.getClientSocket() + " enviou a requisição: " + line);
				this.handleAction(tokens, line);				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void handleAction(String[] tokens, String line) {
		String comando = tokens[0];
		if (comando.equals(LOGOFF_COMMAND)) {
			handleLogoff(client);
		} else if (comando.equalsIgnoreCase(LOGIN_COMMAND)) {
			handleLogin(client, tokens);
		} else if (comando.equalsIgnoreCase(SEND_MESSAGE_TO_COMMAND)) {			
			handleMessage(client ,line);				
//		} else if ("join".equalsIgnoreCase(comando)) {			
//			notificaClientViaOutput(client, "Feature ainda não disponível.\n"); //handleJoin(tokens);
//		} else if ("leave".equalsIgnoreCase(comando)) {
//			notificaClientViaOutput(client, "Feature ainda não disponível.\n"); //handleLeave(tokens);
		} else if (comando.equalsIgnoreCase(SHOW_MY_CURRENT_USER_COMMAND)){
			showUser(client);
		} else if (comando.equalsIgnoreCase(SHOW_ONLINE_USER_LIST_COMMAND)) {
			showOnlineUserList(client);
		} else {
			notificaClientViaOutput(client, "Comando " + comando + " não pode ser reconhecido.\n");
		}
	}

}
