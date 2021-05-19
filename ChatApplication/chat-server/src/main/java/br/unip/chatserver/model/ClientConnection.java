package br.unip.chatserver.model;

import br.unip.chatserver.controler.ClientActionHandler;

import java.io.*;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

import javax.validation.Valid;

public class ClientConnection extends Thread {

	@Valid
	private Usuario user = null;

	private final Socket clientSocket;

//	private OutputStream outputStream;
	
	private ObjectOutputStream objectOutputStream;
	
//	private InputStream inputStream;
	
	private ObjectInputStream objectInputStream;
	
	private BufferedReader bufferedReader;

	public ClientConnection(Socket clientSocket) {
		this.clientSocket = clientSocket;
//		this.inputStream = this.iniciaInputStream();
//		this.outputStream = this.iniciaClientOuputStream();
		this.objectInputStream = this.iniciaClientObjectInputStream();
		this.objectOutputStream = this.iniciaClientObjectOuputStream();
//		this.bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
	}	

	@Override
	public void run() {
		ClientActionHandler clientActionHandler = new ClientActionHandler(this);
		while (isClientConnected()) {
			try {
				clientActionHandler.clientListener();
			} catch (SocketException e) {
				Server.removeClientConnection(this);
				break;
			}
		}
		this.interrupt();
	}		

	private boolean isClientConnected() {
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
	
//	public OutputStream getOutputStream() {
//		return objectOutputStream;
//	}
	
	public ObjectOutputStream getObjectOutputStream() {
		return objectOutputStream;
	}
	
//	public InputStream getInputStream() {
//		return inputStream;
//	}
	
	public ObjectInputStream getObjectInputStream() {
		return objectInputStream;
	}
	
	public BufferedReader getBufferedReader() {
		return bufferedReader;
	}
	
	public Socket getClientSocket() {
		return clientSocket;
	}

	private OutputStream iniciaClientOuputStream() { 
		try {
			return clientSocket.getOutputStream();
		} catch (IOException e) {
			System.err.print("Não foi possível iniciar o outputStream do " + this + ".\nMotivo: " + e.getCause());
		}
		return objectOutputStream;
	}
	
	private ObjectOutputStream iniciaClientObjectOuputStream() {
		try {
			return new ObjectOutputStream(clientSocket.getOutputStream());
		} catch (IOException e) {
			System.err.print("Não foi possível iniciar o ObjectOutputStream do " + this + ".\nMotivo: " + e.getCause());
		}
		return objectOutputStream;
	}
	
	private ObjectInputStream iniciaClientObjectInputStream() {
		try {
			return new ObjectInputStream(clientSocket.getInputStream());
		} catch (IOException e) {
			System.err.print("Não foi possível iniciar o ObjectInputStream do " + this + ".\nMotivo: " + e.getCause());
		}
		return objectInputStream;
	}
	
//	private InputStream iniciaInputStream() {
//		try {
//			return clientSocket.getInputStream();
//		} catch (IOException e) {
//			System.err.print("Não foi possível iniciar o InputStream do " + this + ".\nMotivo: " + e.getCause());
//		}
//		return inputStream;
//	}
	
	public boolean isUserLogado() {
		return this.getUser() != null;
	}
	
}
