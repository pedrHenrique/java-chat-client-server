package br.unip.chatserver.model.clientactions;

import static br.unip.chatserver.constants.ServerCommands.ACCEPT_MESSAGE_FROM_COMMAND;
import static br.unip.chatserver.constants.ServerCommands.SEND_MESSAGE_TO_COMMAND;
import static br.unip.chatserver.constants.ServerResponse.FAIL;
import static br.unip.chatserver.constants.ServerResponse.OK;
import static br.unip.chatserver.model.clientactions.ClientNotificationActions.notificaClientViaOutput;
import static br.unip.chatserver.model.clientactions.util.ActionsUtil.validaToken;
import static br.unip.chatserver.model.clientactions.util.ActionsUtil.retornaClienteComUsuarioInformado;

import org.apache.commons.lang3.StringUtils;

import br.unip.chatserver.constants.ServerResponse;
import br.unip.chatserver.model.ChatMessage;
import br.unip.chatserver.model.ClientConnection;

public final class ClientMessageActions {
	
	private ClientMessageActions() {
		
	}
	
	protected static String[] validaTokenMensagem(String linha) {
		String[] tokens = formataTokenMensagem(linha);
		return (validaToken(tokens)) ? tokens : null; //Retorna null se os tokens não forem válidos	
	}
	
	// retorna a seguinte 'array': msg, destinatario, 'Uma mensagem completa, podendo conter espaços e outras paradas'
	protected static String[] formataTokenMensagem(String linha) {
		return StringUtils.split(linha, null, 3);
	}

	protected static void notificaUsuarios(ClientConnection clientRemetente, String destinatario, String mensagem) {
		if (usuarioEstaTentandoEnviarUmaMensagemPraEleMesmo(clientRemetente, destinatario)) {
			notificaClientViaOutput(clientRemetente, "Falha. Porquê você estaria tentando enviar uma mensagem para você mesmo?\n");
			return;
		}
		ClientConnection clientDestinatario = retornaClienteComUsuarioInformado(destinatario);
		if (clientDestinatario != null) {
			ChatMessage chatMensagem = new ChatMessage(clientRemetente.getUser(), mensagem, clientDestinatario.getUser());
			notificaClientViaOutput(clientDestinatario, ACCEPT_MESSAGE_FROM_COMMAND + " " + chatMensagem.getRemetente().getLogin() + " " + chatMensagem.toString());
			notificaClientViaOutput(clientRemetente, SEND_MESSAGE_TO_COMMAND + " " + clientDestinatario.getUser().getLogin() + " " + OK + " " + chatMensagem.toString()); // TODO As respostas enviadas por requisições poderiam estar em um JSON bem formatado
			return;
		}
		notificaClientViaOutput(clientRemetente, SEND_MESSAGE_TO_COMMAND + " " + destinatario + " " + FAIL + destinatario + " não está online no momento.\n");
	}
	
	private static boolean usuarioEstaTentandoEnviarUmaMensagemPraEleMesmo(ClientConnection clientRemetente, String destinatario) {
		return clientRemetente.getUser().getLogin().equalsIgnoreCase(destinatario);
	}	
	
	protected static String formataMensagemChat(ClientConnection remetente, String mensagem) {
		return remetente.getUser().getLogin() + ": " + mensagem + "\n";
	}

}
