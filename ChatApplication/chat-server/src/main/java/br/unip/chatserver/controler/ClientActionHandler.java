package br.unip.chatserver.controler;

import static br.unip.chatserver.model.clientactions.ClientNotificationActions.notificaClientViaOutput;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;

import br.unip.chatserver.model.ClientConnection;
import br.unip.chatserver.model.Server;
import br.unip.chatserver.model.clientactions.ClientActions;

public final class ClientActionHandler extends ClientActions {
	
	private final ClientConnection client;

	public ClientActionHandler(ClientConnection client) {
		this.client = client;
	}
	
	public void clientListener() {
		try {
			String line = client.getBufferedReader().readLine();//new BufferedReader(new InputStreamReader(inputStream)).readLine();
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
		if ("logoff".equals(comando) || "quit".equalsIgnoreCase(comando)) {
			handleLogoff(client);
		} else if ("login".equalsIgnoreCase(comando)) {
			handleLogin(client, tokens);
		} else if ("msg".equalsIgnoreCase(comando)) {			
			handleMessage(client ,line);				
		} else if ("join".equalsIgnoreCase(comando)) {			
			notificaClientViaOutput(client, "Feature ainda não disponível.\n"); //handleJoin(tokens);
		} else if ("leave".equalsIgnoreCase(comando)) {
			notificaClientViaOutput(client, "Feature ainda não disponível.\n"); //handleLeave(tokens);
		} else if ("user".equalsIgnoreCase(comando)){
			showUser(client);
		} else if ("userlist".equalsIgnoreCase(comando)) {
			showOnlineUserList(client);
		} else {
			notificaClientViaOutput(client, "Comando " + comando + " não pode ser reconhecido.\n");
		}
	}

}
