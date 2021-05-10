package br.unip.chatclient.model.server;

public interface ServerEvents {
	
	public void onlineUser(String usuario);
	
	public void offlineUser(String usuario);
	
	public void messageReceved(String user, String message);
	
	public void messageSent(String user, String status, String message);

}
