package br.unip.chatclient.model.server;

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

	private InputStream serverIn; // Recebe uma mensagem!!

	private OutputStream serverOut; // Envia uma solicitação ao servidor!!

	private BufferedReader bufferedIn;

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
		} catch (IOException e) {
			throw new IOException("Não foi possível se conectar ao servidor no momento. Tente novamente mais tarde.");
		}
		this.iniciaFerramentasDaConexao();
	}

	private OutputStream iniciaOuputStream() throws IOException {
		try {
			return socket.getOutputStream();
		} catch (IOException e) {
			throw new IOException("Não foi possível iniciar o outputStream.nMotivo: " + e.getStackTrace() + ".\n O Programa será encerrado!");			
		}
	}

	private InputStream iniciaInputStream() throws IOException {
		try {
			return socket.getInputStream();
		} catch (IOException e) {
			throw new IOException("Não foi possível iniciar o InputStream.\nMotivo: " + e.getStackTrace() + ".\n O Programa será encerrado!");
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
	
	public Socket getSocket() {
		return socket;
	}

	public ServerCommunication retornaComunicadorComServidor() {
		return new ServerCommunication(this);
	}

	//TODO Se a comunicação já tiver sido fechada, ele cai na exception 
	/**
	 * Finaliza comunicacao com servidor.
	 *
	 * @param conexao - A conexão que será fechada.
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void finalizaComunicacaoComServidor() {
		try {
			this.socket.close();
			this.serverIn = null;
			this.serverOut = null;
			this.bufferedIn = null;
		} catch (Exception e) {
			System.exit(0);
		}
	}
	
}