package br.unip.chatserver.model;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public class Server {

	//private static final Logger LOGGER = Logger.getLogger(Server.class.getName());

	private final Integer serverPort;

	private static ServerSocket serverSocket;

	private static ArrayList<ClientConnection> clientList = new ArrayList<>();

	public Server(Integer serverPort) {
		this.serverPort = serverPort;
	}

	public void run() {
		iniciaServerSocket(serverPort);
		while (serverSocket.isBound()) {
			System.out.println("Pronto para receber conexões...");
			aceitaConexaoCliente();
		}
	}
	
	private void aceitaConexaoCliente() {
		try {
			Socket clientSocket = serverSocket.accept();
			System.out.println("Conexão aceita do soquete: " + clientSocket);
			this.administraCliente(clientSocket);
		} catch (IOException exception) {
			System.err.print("Server Error. O Servidor não pôde aceitar uma conexão!\nMotivo: " + exception.getMessage());
		}
	}

	private void administraCliente(Socket cliente) {
		ClientConnection client = new ClientConnection(cliente);
		clientList.add(client);
		client.start();
	}

	private static void iniciaServerSocket(Integer serverPort) {
		if (serverSocket == null) {
			try {
				serverSocket = new ServerSocket(serverPort);
				System.out.println("Servidor Chat Iniciado!");
				return;
			} catch (IOException e) {
				throw new IllegalArgumentException("Server Error. Não foi possível inicializar o servidor!\nMotivo: " + e.getMessage());
			}
		}
		System.out.println("Apenas uma instância do servidor pode estar rodando.");
	}

	private static void finalizaSocketServer() {
		if (serverSocket != null && !serverSocket.isClosed()) {
			try {
				serverSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
				System.err.print("Não foi possível fechar a conexão do servidor\n");
			}
		}
	}

	public static List<ClientConnection> getClientList() {
		return clientList;
	}

	public static void removeClientConnection(ClientConnection client) {
		clientList.remove(client);
	}

	public static void notificaNoConsoleDoServidor(String mensagem) {
		//LOGGER.info(mensagem);
		System.out.println(mensagem);
	}
}
