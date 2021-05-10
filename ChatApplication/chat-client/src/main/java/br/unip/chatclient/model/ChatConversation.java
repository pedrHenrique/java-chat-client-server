package br.unip.chatclient.model;

import java.util.ArrayList;
import java.util.List;

public class ChatConversation implements OpenChatConversation{
	
	// FIXME - No Futuro, trocar as String dos usuários pelos próprios objetos deles mesmos
	
	private final String usuarioUm; // Usuário principal do Chat
	
	private final String usuarioDois; 
	
	private final List<String> conversa = new ArrayList<>();
	
	public ChatConversation(String usuarioUm, String usuarioDois) {
		this.usuarioUm = usuarioUm;
		this.usuarioDois = usuarioDois;
		OpenChatConversation.addConversation(this);
	}
	
	public void addMessage(String message) {
		conversa.add(message);
	}
	
	public void removeMessage(String message) {
		conversa.remove(message);
	}
	
	public List<String> getConversa() {
		return conversa;
	}

	public String getUsuarioUm() {
		return usuarioUm;
	}

	public String getUsuarioDois() {
		return usuarioDois;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((usuarioDois == null) ? 0 : usuarioDois.hashCode());
		result = prime * result + ((usuarioUm == null) ? 0 : usuarioUm.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		ChatConversation other = (ChatConversation) obj;
		if (usuarioDois == null) {
			if (other.usuarioDois != null) {
				return false;
			}
		} else if (!usuarioDois.equals(other.usuarioDois)) {
			return false;
		}
		if (usuarioUm == null) {
			if (other.usuarioUm != null) {
				return false;
			}
		} else if (!usuarioUm.equals(other.usuarioUm)) {
			return false;
		}
		return true;
	}

}
