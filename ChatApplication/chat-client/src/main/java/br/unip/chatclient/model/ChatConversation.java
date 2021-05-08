package br.unip.chatclient.model;

public class ChatConversation {
	
	private final String usuarioUm;
	
	private final String usuarioDois; //Classe para próprio usuário
	
	// Map com listas para histórico das mensagens
	
	public ChatConversation(String usuarioUm, String usuarioDois) {
		this.usuarioUm = usuarioUm;
		this.usuarioDois = usuarioDois;
	}
	
	

}
