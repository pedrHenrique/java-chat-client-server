package br.unip.chatclient.model.server;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;

import br.unip.chatclient.controler.ServerListener;

/**
 *
 * Classe com o intuito de mediar a comunicação entre um Chat Conectado e o
 * Servidor em si.<p> 
 * 
 * A conversa com o servidor foi inicialmente pensada em ser feita <i>manualmente</i>.<br>
 * Exemplo: Sempre que eu fazer uma requisição, eu devo obter a requisição esperada que eu solicitei.<p>
 * <b>Problema:</b><br>
 * Existem eventos que não são esperados... Exemplo: Login de um usuário ou uma mensagem recebida. 
 * Então o envio e aguardo de requisições manuais não é funcional nesse exemplo.<p>
 * 
 * Com base nisso foi criada a classe {@link ServerListener}
 * 
 */
public final class ServerCommunication {

	/**
	 * Para se comunicar com um servidor, é necessário possuir uma conexão ativa com
	 * o mesmo
	 **/
	private final ServerConnection connection;

	private static final String SUCESSO = "sucesso";

	private static final String FALHA = "falha";

	public ServerCommunication(ServerConnection connection) {
		this.connection = connection;
	}

	public void doLogin(String usuario, String senha) throws IOException, IllegalArgumentException {
		this.validaComunicacaoComServidor();
		final String comandoLogin = "login ";
		String comando = comandoLogin + usuario + " " + senha + "\n";
		enviaComandoParaServer(comando);
		String resposta = retornaRespostaServidor();
		if (!resposta.contains(SUCESSO)) {
			throw new IllegalArgumentException(resposta);
		}
	}

	public String retornaUsuario() throws IOException {
		enviaComandoParaServer("user\n");
		String resposta = retornaRespostaServidor();
		if (resposta.contains(FALHA)) {
			throw new IOException(resposta);
		}
		return resposta;
	}

	public void doLogoff() throws IOException {
		this.validaComunicacaoComServidor();
		final String comandoLogoff = "logoff\n";
		enviaComandoParaServer(comandoLogoff);
		String resposta = retornaRespostaServidor();
		if (isRespostaEsperada(resposta)) {
			connection.finalizaComunicacaoComServidor();
			this.user = null;
		} else {
			throw new IOException(resposta);
		}
	}

	public void doMensagem(String destinatario, String mensagem) throws IOException {
		this.validaComunicacaoComServidor();
		final String comandoMensagem = "sendTo";
		String comando = String.valueOf(comandoMensagem + " " + destinatario + " " + mensagem + "\n");
		enviaComandoParaServer(comando);
		// Desativado. A Thread que está escutando o servidor que deve cuidar da resposta do mesmo
		
		//String resposta = retornaRespostaServidor();
		//if (resposta.contains(FALHA)) {
		//	throw new IllegalArgumentException(resposta);
		//}
	}

//	private void handleMessage(String[] tokensMsg) {
//        String login = tokensMsg[1];
//        String msgBody = tokensMsg[2];
//
//        for(MessageListener listener : messageListeners) {
//            listener.onMessage(login, msgBody);
//        }
//    }

	private boolean isRespostaEsperada(String resposta) {
		return resposta.contains(SUCESSO) || resposta.isEmpty();
	}

	// Lendo o retorno da requisição
	private String retornaRespostaServidor() throws IOException {
		try {
			// O servidor nem sempre retorna alguma mensagem quando uma solicitação é feita.
			// Se a reposta for nula, retorna um simples vázio para evitar um
			// NullPointerException
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
			throw new IOException(
					"Não foi possível realizar a solicitação para o Servidor. Você não está conectado no momento.\n");
		}
	}

	public ServerConnection getConnection() {
		return connection;
	}

	// Testes com a lista de usuários Online //

	private String user;

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String returnUserList() throws IOException, IllegalArgumentException {
		this.validaComunicacaoComServidor();
		final String comandoLogoff = "userlist\n";
		enviaComandoParaServer(comandoLogoff);
		String resposta = retornaRespostaServidor();
		if (resposta.equals("")) {
			return StringUtils.EMPTY;
		} else {
			return resposta;
		}
	}

}
