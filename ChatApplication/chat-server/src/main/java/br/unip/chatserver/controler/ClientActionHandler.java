package br.unip.chatserver.controler;

import static br.unip.chatserver.clientactions.ClientNotificationActions.notificaClientViaOutput;
import static br.unip.chatserver.clientactions.ClientNotificationActions.notificaClientViaOutputForFile;
import static br.unip.chatserver.constants.ServerCommands.*;

import java.io.IOException;
import java.net.SocketException;

import org.apache.commons.lang3.StringUtils;

import br.unip.chatserver.clientactions.ClientActions;
import br.unip.chatserver.model.ClientConnection;
import br.unip.chatserver.model.Data;
//import br.unip.chatserver.model.FileObject;
import br.unip.chatserver.model.Server;

import model.FileObjectData;

public final class ClientActionHandler extends ClientActions {

	private final ClientConnection client;

	public ClientActionHandler(ClientConnection client) {
		this.client = client;
	}

	public void clientListener() throws SocketException {
		try {
			FileObjectData readObject = (FileObjectData) client.getObjectInputStream().readObject();
			boolean existeArquivo = verificaSeExisteEnvioDeArquivo(readObject);
			if (existeArquivo == false) {
				String line = readObject.getComando();
				String[] tokens = (line != null) ? StringUtils.split(line) : null;
				if (tokens != null && tokens.length > 0) {
					Server.notificaNoConsoleDoServidor(client.getClientSocket() + " enviou a requisição: " + line);
					this.handleAction(tokens, line);
				}
				return;
			}
			this.handleFile(readObject);
			
		} catch (IOException | ClassNotFoundException e) {
			Server.notificaNoConsoleDoServidor("Um erro fatal aconteceu com a conexão do cliente: " + client.toString()
					+ "\nEncerrando a execução deste cliente.");
			throw new SocketException();
		}
	}

	private boolean verificaSeExisteEnvioDeArquivo(FileObjectData readObject) {
		return (readObject != null) ? readObject.getFileName() != null : false;
	}

	// TODO SWITCH CASE aqui
	private void handleAction(String[] tokens, String line) {
		String comando = tokens[0];
		if (comando.equals(LOGOFF_COMMAND)) {
			handleLogoff(client);
		} else if (comando.equalsIgnoreCase(LOGIN_COMMAND)) {
			handleLogin(client, tokens);
		} else if (comando.equalsIgnoreCase(SEND_MESSAGE_TO_COMMAND)) {
			handleMessage(client, line);
		} else if (comando.equalsIgnoreCase(SHOW_MY_CURRENT_USER_COMMAND)) {
			showUser(client);
		} else if (comando.equalsIgnoreCase(SHOW_ONLINE_USER_LIST_COMMAND)) {
			showOnlineUserList(client);
		} else {
			notificaClientViaOutput(client, "Comando " + comando + " não pode ser reconhecido.\n");
		}
	}
	
	public void handleFile(FileObjectData data) throws IOException {
		notificaClientViaOutputForFile(data);
	}

}
