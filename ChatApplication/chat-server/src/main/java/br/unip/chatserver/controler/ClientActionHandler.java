package br.unip.chatserver.controler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.lang3.StringUtils;

import br.unip.chatserver.model.ClientActions;
import br.unip.chatserver.model.ClientConnection;

public final class ClientActionHandler extends ClientActions {
	
	private final ClientConnection client;

	public ClientActionHandler(ClientConnection client) {
		this.client = client;
	}
	
	public void clientListener() {
		try {
			InputStream inputStream = client.getClientSocket().getInputStream();
			String line = new BufferedReader(new InputStreamReader(inputStream)).readLine();
			String[] tokens = (line != null) ? StringUtils.split(line) : null;
			if (tokens != null && tokens.length > 0) {
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
			//String[] tokensMsg = StringUtils.split(line, null, 3);
			//handleMessage(tokensMsg);
		} else if ("join".equalsIgnoreCase(comando)) {
			//handleJoin(tokens);
		} else if ("leave".equalsIgnoreCase(comando)) {
			//handleLeave(tokens);
		} else {
			notificaClientViaOutput(client, "Comando " + comando + " não pode ser reconhecido.\n");
		}
	}

}
