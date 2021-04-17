package br.unip.chatserver.model;

import br.unip.chatserver.controler.ClientActionHandler;

import java.io.*;
import java.net.Socket;
import java.util.HashSet;

import javax.validation.Valid;

public class ClientConnection extends Thread {

	@Valid
	private Usuario user = null;

	private final Socket clientSocket;

	private OutputStream outputStream;	

	private HashSet<String> topicSet = new HashSet<String>();

	public ClientConnection(Socket clientSocket) {
		this.clientSocket = clientSocket;
		this.outputStream = this.iniciaClientOuputStream();
	}	

	@Override
	public void run() {
		ClientActionHandler clientActionHandler = new ClientActionHandler(this);
		while (clientIsConnected()) {
			clientActionHandler.clientListener();
		}
	}		

	private boolean clientIsConnected() {
		return !clientSocket.isClosed();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((clientSocket == null) ? 0 : clientSocket.hashCode());
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
		ClientConnection other = (ClientConnection) obj;
		if (clientSocket == null) {
			if (other.clientSocket != null) {
				return false;
			}
		} else if (!clientSocket.equals(other.clientSocket)) {
			return false;
		}
		return true;
	}	
	
	public Usuario getUser() {
		return user;
	}
	
	public void setUser(Usuario user) {
		this.user = user;
	}
	
	public OutputStream getOutputStream() {
		return outputStream;
	}
	
	public Socket getClientSocket() {
		return clientSocket;
	}

	private OutputStream iniciaClientOuputStream() {
		try {
			return clientSocket.getOutputStream();
		} catch (IOException e) {
			System.err.print("Não foi possível iniciar o outputStream dp " + this + ".\nMotivo: "
					+ e.getStackTrace());
		}
		return outputStream;
	}
	
	public boolean isUserLogado() {
		return this.getUser() != null;
	}
	
}
