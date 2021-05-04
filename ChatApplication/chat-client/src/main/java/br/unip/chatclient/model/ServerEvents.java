package br.unip.chatclient.model;

public interface ServerEvents {
	
	public void onlineUser(String usuario);
	
	public void offlineUser(String usuario);
	
	//TODO messageReceved/MessageSent

}
