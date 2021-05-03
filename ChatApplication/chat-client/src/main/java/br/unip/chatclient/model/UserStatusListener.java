package br.unip.chatclient.model;

public interface UserStatusListener {
	
	public void online(String login);
    
	public void offline(String login);
	
}
