package br.unip.chatserver.constants;

public final class ServerCommands {
	
	public static final String LOGIN_COMMAND = "login";
	
	public static final String LOGOFF_COMMAND = "logoff";
	
	/** Comando para enviar mensagem para algum Chat */
	public static final String SEND_MESSAGE_TO_COMMAND = "sendTo";
	
	/** Comando para notificar uma mensagem recebida de algum Chat */
	public static final String ACCEPT_MESSAGE_FROM_COMMAND = "acceptFrom";
	
	public static final String SHOW_MY_CURRENT_USER_COMMAND = "user";
	
	public static final String SHOW_ONLINE_USER_LIST_COMMAND = "userlist";
	
	public static final String ACCEPT_FILE_FROM_COMMAND = "fileAcceptFrom";
	
	public static final String SEND_FILE_TO_COMMAND = "sendFileTo";
	
	/** Comando necessário para enviar algum comando ou notificação pelo chat. **/
	public static final String ENTER_COMMAND = "\n";
	
	private ServerCommands() {
		
	}

}
