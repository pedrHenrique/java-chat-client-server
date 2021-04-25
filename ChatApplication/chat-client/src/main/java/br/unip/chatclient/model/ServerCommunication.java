package br.unip.chatclient.model;

import java.io.IOException;

/**
 * The Class ServerCommunication.
 * 
 * <p>Classe com o intuito de mediar a comunicação entre um Chat Conectado 
 * e o Servidor em si.
 * 
 * Melhorar essa documentação
 */
public final class ServerCommunication {	

	/** Para se comunicar com um servidor, é necessário possuir uma conexão ativa com o mesmo **/
	private final ServerConnection connection;
	
	private static final String SUCESSO = "sucesso";
	
	private static final String FALHA = "falha";
	
	// serverResponse
	
	public ServerCommunication(ServerConnection connection) {
		this.connection = connection;
	}
	
	public boolean doLogin(String usuario, String senha) throws IOException, IllegalArgumentException {		
		final String COMANDO_LOGIN = "login ";
		// Validar a conexão.
		this.validaComunicacaoComServidor();
		
		// Enviando uma requisição ao servidor
		String comando = COMANDO_LOGIN + usuario + " " + senha + "\n";
		connection.getServerOut().write(comando.getBytes());
		
		// Lendo o retorno da requisição
		String resposta = connection.getBufferedIn().readLine();
        
        if (resposta.contains(SUCESSO)) {
        	// do something
        	return true;
        } else {
        	throw new IllegalArgumentException(resposta);
        }
	}

	private void validaComunicacaoComServidor() throws IOException {
		if (!connection.isConexaoComServidorEstabelecida()) {
			throw new IOException("Não foi possível realizar o login. Você não está conectado ao servidor\n");
		}
	}

	public ServerConnection getConnection() {
		return connection;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((connection == null) ? 0 : connection.hashCode());
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
		ServerCommunication other = (ServerCommunication) obj;
		if (connection == null) {
			if (other.connection != null) {
				return false;
			}
		} else if (!connection.equals(other.connection)) {
			return false;
		}
		return true;
	}
	
	

}
