package br.unip.chatclient.model;

import java.io.IOException;

import br.unip.chatclient.util.notifier.UserMessageNotifier;

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
	
	public void doLogin(String usuario, String senha) throws IOException, IllegalArgumentException {				
		// Validar a conexão.
		this.validaComunicacaoComServidor();		
		final String comandoLogin = "login ";
		String comando = comandoLogin + usuario + " " + senha + "\n";
		enviaComandoParaServer(comando);
		String resposta = retornaRespostaServidor();
        if (resposta.contains(SUCESSO)) {
        	//
        } else {
        	throw new IllegalArgumentException(resposta);
        }
	}
	
	public void doLogoff() throws IOException, IllegalArgumentException {
		this.validaComunicacaoComServidor();
		final String comandoLogoff = "logoff\n";
		enviaComandoParaServer(comandoLogoff);
		String resposta = retornaRespostaServidor();
		if (isRespostaEsperada(resposta)) {
			connection.finalizaComunicacaoComServidor();
		} else {
			throw new IllegalArgumentException(resposta);
		}
	}

	private boolean isRespostaEsperada(String resposta) {
		return resposta.contains(SUCESSO) || resposta.isEmpty();
	}

	// Lendo o retorno da requisição
	private String retornaRespostaServidor() throws IOException {
		try {
			// O servidor nem sempre retorna alguma mensagem quando uma solicitação é feita.
			// Se a reposta for nula, retorna um simples vázio para evitar um NullPointerException
			String resposta = connection.getBufferedIn().readLine();
			return (resposta != null) ? resposta : "";
		} catch (IOException e) {			
			throw new IOException("Não foi possível ler a resposta do servidor!!\nMotivo: " + e.getCause()); 
		}
	}

	// Enviando uma requisição ao servidor
	private void enviaComandoParaServer(String comando) throws IOException {
		try {
			connection.getServerOut().write(comando.getBytes());
		} catch (Exception e) {
			throw new IOException("Não foi possível enviar uma requisição do servidor!!\nMotivo: " + e.getCause()); 
		}		
	}

	private void validaComunicacaoComServidor() throws IOException {
		if (!connection.isConexaoComServidorEstabelecida()) {
			throw new IOException("Não foi possível realizar a solicitação para o Servidor. Você não está conectado no momento.\n");
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
