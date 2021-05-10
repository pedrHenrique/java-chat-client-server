package br.unip.chatclient.model;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public interface OpenChatConversation {
	
	public static List<ChatConversation> openChats = new ArrayList<>();
	
	public static void addConversation(ChatConversation openChat) {
		openChats.add(openChat);
	}
	
	/**
	 * Retorna um chat que já foi aberto em algum momento em um chat.
	 *
	 * @param usuarioUm - Um dos usuários presentes na conversa.
	 * @param usuarioDois - O outro usuário presente na conversa.
	 * @return O próprio chat cotendo todas as mensagens que ambos estavam trocando.
	 * @throws NoSuchElementException Se o chat não for encontrado!
	 */
	public static ChatConversation returnOpenChat(String usuarioUm, String usuarioDois) throws NoSuchElementException {
		for (ChatConversation chatConversation : openChats) {
			if ((chatConversation.getUsuarioUm().equals(usuarioUm) || chatConversation.getUsuarioUm().equals(usuarioDois)) && 
			   (chatConversation.getUsuarioDois().equals(usuarioUm) || chatConversation.getUsuarioDois().equals(usuarioDois))) {
				return chatConversation;
			}
		}
		throw new NoSuchElementException("Não foi possível reabir o chat solicitado!");
	}
}
