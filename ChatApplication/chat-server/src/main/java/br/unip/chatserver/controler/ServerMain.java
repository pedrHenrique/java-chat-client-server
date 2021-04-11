package br.unip.chatserver.controler;

import br.unip.chatserver.model.Server;

public class ServerMain {
	
	private static final Integer PORT = 8818;
	
    public static void main(String[] args) {        
        Server server = new Server(PORT);
        server.run();
    }
}
