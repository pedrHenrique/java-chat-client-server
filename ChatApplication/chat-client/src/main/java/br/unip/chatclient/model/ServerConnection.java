package br.unip.chatclient.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class ServerConnection {
	
	private final String serverAddres;
    
    private final Integer serverPort;
    
    private Socket socket;
    
    private InputStream serverIn;
    
    private OutputStream serverOut; // Envia uma solicitação ao servidor?
    
    private BufferedReader bufferedIn;
    
    private String connectionStatusMessage;

	public ServerConnection(String serverAddres, Integer serverPort) {
		this.serverAddres = serverAddres;
		this.serverPort = serverPort;
	}
	
	private ServerConnection iniciaFerramentasDaConexao() throws IOException {
		this.serverIn = this.iniciaInputStream();
		this.serverOut = this.iniciaOuputStream();
		this.bufferedIn = new BufferedReader(new InputStreamReader(serverIn));
		return this;
	}
    
	public void connect() throws IOException {
        try {
            this.socket = new Socket(serverAddres, serverPort);
            this.iniciaFerramentasDaConexao();
            this.setConnectionStatusMessage("Conexão realizada com Sucesso!");
        } catch (IOException e) {
        	this.setConnectionStatusMessage("Não foi possível se conectar ao servidor.\nMotivo: " + e.getMessage() + ".");
            throw new IOException();
        }
    }
	
	private OutputStream iniciaOuputStream() throws IOException {
		try {
			return socket.getOutputStream();
		} catch (IOException e) {			
			this.setConnectionStatusMessage("Não foi possível iniciar o outputStream.nMotivo: " + e.getStackTrace() + ".");
			throw new IOException();
		}
	}
	
	private InputStream iniciaInputStream() throws IOException {
		try {
			return socket.getInputStream();
		} catch (IOException e) {
			this.setConnectionStatusMessage("Não foi possível iniciar o InputStream.\nMotivo: " + e.getStackTrace() + ".");
			throw new IOException();
		}
	}

	public boolean isConexaoComServidorEstabelecida() {
		return !this.socket.isClosed();
	}
	
	public BufferedReader getBufferedIn() {
		return bufferedIn;
	}
	
	public InputStream getServerIn() {
		return serverIn;
	}
	
	public OutputStream getServerOut() {
		return serverOut;
	}
	
	public String getConnectionStatusMessage() {
		return connectionStatusMessage;
	}
	
	public void setConnectionStatusMessage(String connectionStatusMessage) {
		this.connectionStatusMessage = connectionStatusMessage;
	}
}
