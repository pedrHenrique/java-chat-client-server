package br.unip.chatclient.model;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;

import br.unip.chatclient.view.Chat;

/**
 * ServerListener - A ideia é escutar e tratar, <b>todo tipo de informação enviada automaticamente pelo servidor</b>.<br>
 * E claro, aplicar as devidas ações tratadas no Chat correspondente!.<p>
 * 
 * Essa Classe é uma extensão de uma Thread. Então podemos continuar utilizando o chat normalmente que esse carinha aqui
 * ficará escutando tudo o que o servidor estiver dizendo em background.<p>
 * 
 * Descrição gerada automaticamente lol \/<p>
 * 
 * The listener interface for receiving server events.
 * The class that is interested in processing a server
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addServerListener<code> method. When
 * the server event occurs, that object's appropriate
 * method is invoked.
 *
 * @see ServerEvent
 */
public class ServerListener extends Thread {

	private ServerConnection connection;
	
	private Chat chat;

	public ServerListener(Chat chat, ServerConnection connection) {
		this.chat = chat;
		this.connection = connection;
	}

	@Override
	public void run() {
		String line;
		System.out.println("Iniciando Listener");
		try {
			while ((line = connection.getBufferedIn().readLine()) != null) {
				String[] tokens = StringUtils.split(line);
				if (tokens != null && tokens.length > 0) {
					System.out.println(line);
                    String cmd = tokens[0];
                    if ("online".equalsIgnoreCase(cmd)) {
                        chat.onlineUser(tokens[1]);
                    } else if ("offline".equalsIgnoreCase(cmd)) {
                    	chat.offlineUser(tokens[1]);
                    } else {
                    	System.out.println("Comando não reconhecido recebido!");
                    }
//						  else if ("msg".equalsIgnoreCase(cmd)) {
//                        String[] tokensMsg = StringUtils.split(line, null, 3);
//                        handleMessage(tokensMsg);
//                    }
				}
			}
		} catch (IOException e) {
			System.err.print("Uma das thread que ficam ouvindo o servidor acabaram falhando. Pedindo que ela rode novamente!\nMotivo da falha: " + e);
			this.interrupt();
		}
	}
}
