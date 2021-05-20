package br.unip.chatclient.view;

import br.unip.chatclient.controler.ServerListener;
import br.unip.chatclient.model.ChatConversation;
import br.unip.chatclient.model.FileObject;
import br.unip.chatclient.model.Usuario;
import br.unip.chatclient.model.server.ServerCommunication;
import br.unip.chatclient.model.server.ServerEvents;
import br.unip.chatclient.util.notifier.UserMessageNotifier;

import static br.unip.chatclient.model.OpenChatConversation.returnOpenChat;

import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import org.json.JSONObject;

import model.FileObjectData;

public class Chat extends JFrame implements ServerEvents{

	private static final String CARACTER_NOTIFICADOR = " +";

	/*
	 * Basicamente o que temos agora!!
	 * 
	 * Sempre que um usuário logar, ele será redirecionado a esse tela de Chat. A
	 * tela de chat atualmente consegue pegar a lista de usuários logados NO MOMENTO
	 * QUE O USU�?RIO ESPEC�?FICO ENTROU NESTA TELA.
	 * 
	 * Ou seja, se um usuário novo entrar ou sair, a lista não vai se atualizar
	 * automaticamente... Ainda não.
	 * 
	 * 
	 * Requisições manuais quebram a thread principal
	 */

	private ServerCommunication serverCommunication;

	private Usuario usuario;
	
	private List<ChatConversation> listOpenChats = new ArrayList<>();
	
	private ChatConversation activeChat;
	
	// Para adicionar elementos na lista de mensagem. Nome destaVariavel.addElement(StringQualquer)
	private DefaultListModel<String> listUserModule = new DefaultListModel<>();

	private DefaultListModel<String> listChatMessageModule = new DefaultListModel<>();

	public Chat(ServerCommunication serverCommunication) throws IOException {
		this.serverCommunication = serverCommunication;
		this.initComponents();
		this.realizaSolicitacoesInicias();
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE); // TODO - Até descobrir como escolher customizar a forma de fechar o form. O botão de fechar vai ficar "desativado".
		this.setVisible(true);
		this.paneMensagemArea.setVisible(false);
		this.lblDestinatario.setText("");
		new ServerListener(this, serverCommunication.getConnection()).start();
	}
	
	public Chat() {
		this.initComponents();
	}
	
	private void realizaSolicitacoesInicias() throws IOException {
		JSONObject jo = new JSONObject(serverCommunication.retornaUsuario());
		this.usuario = new Usuario(jo.getString("login")); //FIXME Instanciar o usuário utilizando o ID quando eles estiverem presentes no projeto.
		this.lblNomeUsuario.setText(usuario.getLogin());
		this.preencheUserList(serverCommunication);
	}

	private void preencheUserList(ServerCommunication serverCommunication) {
		String userList = null;
		try {
			userList = serverCommunication.returnUserList();
		} catch (IllegalArgumentException | IOException e) {
			UserMessageNotifier.errorMessagePane(this, "Não foi possível atualizar a lista de usuários online...\nMotivo: " + e.getMessage());
			return;
		}
		List<String> listUsuariosOnline = Arrays.asList(userList.split(","));
		for (String usuarioOnline : listUsuariosOnline) {
			listUserModule.addElement(usuarioOnline);
			listOpenChats.add(new ChatConversation(usuario.getLogin(), usuarioOnline));
		}
	}

	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jUserScrollPane = new javax.swing.JScrollPane();
        jUserList = new javax.swing.JList<>(listUserModule);
        paneBottomArea = new javax.swing.JPanel();
        txtMensagem = new javax.swing.JTextField();
        btEnviar = new javax.swing.JButton();
        btnEnviarArquivo = new javax.swing.JButton();
        paneTopArea = new javax.swing.JPanel();
        btSair = new javax.swing.JButton();
        lblOla = new javax.swing.JLabel();
        lblNomeUsuario = new javax.swing.JLabel();
        paneMensagemArea = new javax.swing.JPanel();
        jMessageScrollPane = new javax.swing.JScrollPane();
        jMessagesList = new javax.swing.JList<>(listChatMessageModule);
        lblConversandoCom = new javax.swing.JLabel();
        lblDestinatario = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(102, 102, 102));

        jUserList.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jUserList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jUserListMouseClicked(evt);
            }
        });
        jUserScrollPane.setViewportView(jUserList);

        paneBottomArea.setBackground(new java.awt.Color(204, 204, 204));

        txtMensagem.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        btEnviar.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        btEnviar.setText("Enviar");
        btEnviar.setEnabled(false);
        btEnviar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btEnviarActionPerformed(evt);
            }
        });

        btnEnviarArquivo.setText("Arquivo");
        btnEnviarArquivo.setEnabled(false);
        btnEnviarArquivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEnviarArquivoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout paneBottomAreaLayout = new javax.swing.GroupLayout(paneBottomArea);
        paneBottomArea.setLayout(paneBottomAreaLayout);
        paneBottomAreaLayout.setHorizontalGroup(
            paneBottomAreaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paneBottomAreaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtMensagem, javax.swing.GroupLayout.PREFERRED_SIZE, 490, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btEnviar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEnviarArquivo)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        paneBottomAreaLayout.setVerticalGroup(
            paneBottomAreaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paneBottomAreaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(paneBottomAreaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtMensagem, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btEnviar)
                    .addComponent(btnEnviarArquivo))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        paneTopArea.setBackground(new java.awt.Color(204, 204, 204));

        btSair.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        btSair.setText("Sair");
        btSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSairActionPerformed(evt);
            }
        });

        lblOla.setFont(new java.awt.Font("Goudy Old Style", 1, 18)); // NOI18N
        lblOla.setText("Olá ");

        lblNomeUsuario.setFont(new java.awt.Font("Goudy Old Style", 1, 18)); // NOI18N

        javax.swing.GroupLayout paneTopAreaLayout = new javax.swing.GroupLayout(paneTopArea);
        paneTopArea.setLayout(paneTopAreaLayout);
        paneTopAreaLayout.setHorizontalGroup(
            paneTopAreaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, paneTopAreaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblOla)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblNomeUsuario)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btSair)
                .addContainerGap())
        );
        paneTopAreaLayout.setVerticalGroup(
            paneTopAreaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paneTopAreaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(paneTopAreaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btSair)
                    .addComponent(lblOla)
                    .addComponent(lblNomeUsuario))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        paneMensagemArea.setEnabled(false);

        jMessageScrollPane.setViewportView(jMessagesList);

        lblConversandoCom.setFont(new java.awt.Font("Goudy Old Style", 1, 18)); // NOI18N
        lblConversandoCom.setText("Conversando com: ");

        lblDestinatario.setFont(new java.awt.Font("Goudy Old Style", 1, 18)); // NOI18N

        javax.swing.GroupLayout paneMensagemAreaLayout = new javax.swing.GroupLayout(paneMensagemArea);
        paneMensagemArea.setLayout(paneMensagemAreaLayout);
        paneMensagemAreaLayout.setHorizontalGroup(
            paneMensagemAreaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paneMensagemAreaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(paneMensagemAreaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(paneMensagemAreaLayout.createSequentialGroup()
                        .addComponent(lblConversandoCom)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblDestinatario)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jMessageScrollPane, javax.swing.GroupLayout.Alignment.TRAILING)))
        );
        paneMensagemAreaLayout.setVerticalGroup(
            paneMensagemAreaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, paneMensagemAreaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(paneMensagemAreaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblConversandoCom)
                    .addComponent(lblDestinatario))
                .addGap(18, 18, 18)
                .addComponent(jMessageScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jUserScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(paneBottomArea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(paneMensagemArea, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(20, 20, 20))))
            .addComponent(paneTopArea, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(paneTopArea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(paneMensagemArea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(paneBottomArea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jUserScrollPane))
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnEnviarArquivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEnviarArquivoActionPerformed
        JFileChooser chooser = new JFileChooser();
        int intval = chooser.showOpenDialog(this);
        try {
        	if (intval == JFileChooser.APPROVE_OPTION){
                    //String destinatario = getLblDestinatario().getText(); TODO ME DESCOMENTA PFF
                    File f = chooser.getSelectedFile();
                    String nomeArquivo = f.getName().replace(" ", "_");
                    FileInputStream in = new FileInputStream(f);
                    byte b[] = new byte[in.available()];
                    in.read(b);
                    //FileObject file = new FileObject(usuario.getLogin(), "Teste", nomeArquivo, b);
                    FileObjectData file2 = new FileObjectData(usuario.getLogin(), getLblDestinatario().getText(), nomeArquivo, b);
//                    Data data = new Data();
//                    data.setStatus(jComboBox1.getSelectedItem() + "");
//                    data.setName(txtName.getText().trim());
//                    data.setFile(b);
                    serverCommunication.doFile(file2);
            }	
		} catch (Exception e) {
			UserMessageNotifier.errorMessagePane(this, "Não foi possível enviar o arquivo.\nMotivo: " + e.getMessage());
		}
    }//GEN-LAST:event_btnEnviarArquivoActionPerformed

	private void btSairActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btSairActionPerformed
		try {
			this.serverCommunication.doLogoff();
		} catch (IOException ex) {
			UserMessageNotifier.errorMessagePane(this, ex.getMessage());
		}
	}// GEN-LAST:event_btSairActionPerformed

	private void jUserListMouseClicked(MouseEvent evt) {// GEN-FIRST:event_jUserListMouseClicked
		if (evt.getClickCount() > 1) {
			String userEscolhido = jUserList.getSelectedValue();
			userEscolhido = removeNotificadorDeUsuarioSelecionado(userEscolhido);
			//this.chat = new ChatConversation(usuario.getLogin(), userEscolhido);
			this.paneMensagemArea.setVisible(true);
			this.paneMensagemArea.setEnabled(true);
			this.lblDestinatario.setText(userEscolhido);
			this.txtMensagem.setText("");
			this.listChatMessageModule.clear();
			this.btEnviar.setEnabled(true);
			this.btnEnviarArquivo.setEnabled(true);
			List<String> conversa = retornaChatEntreUsuarios(usuario.getLogin(), userEscolhido);
			adicionaAsMensagensNaTelaDeChat(conversa);
		}
	}// GEN-LAST:event_jUserListMouseClicked

	private List<String> retornaChatEntreUsuarios(String usuarioUm, String usuarioDois) {
		/** 
		 * TODO -> No futuro será necessário fazer uma validação no banco.
		 * Para verificar se os usuários já trocaram mensagens antes.
		 * Se eles nunca tiverem trocado, um novo chat deverá ser aberto, 
		 * agora se eles já tiverem trocado, o returnOpenChat deverá tentar abrir a conversa deles. 
		 */
//		try {
//			this.chat = returnOpenChat(usuarioUm, usuarioDois);
//		} catch (NoSuchElementException e) {
//			// Se cairmos aqui, deu a se entender que os usuários nunca trocaram mensagem antes!
//			this.chat = new ChatConversation(usuarioUm, usuarioDois);
//		}
		activeChat = returnOpenChat(usuarioUm, usuarioDois);
		return activeChat.getConversa();
	}
	
	public void adicionaAsMensagensNaTelaDeChat(List<String> messageList) {
		for (String texto : messageList) {
			listChatMessageModule.addElement(texto);
		}
	}

	private void btEnviarActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btEnviarActionPerformed
		try {
			// Envia requisição para o server
			String destinatario = getLblDestinatario().getText();
			String mensagem = txtMensagem.getText();
			serverCommunication.doMensagem(destinatario, mensagem);
			
			// Adicionando a mensagem na tela
			//listChatMessageModule.addElement(mensagem);
			
			// Limpando o txt mensagem
			txtMensagem.setText("");
		} catch (IOException e) {
			UserMessageNotifier.errorMessagePane(this, "Não foi possível enviar a mensagem para o usuário "
					+ getLblDestinatario().getText() + "\nMotivo: " + e.getMessage());
		}
	}// GEN-LAST:event_btEnviarActionPerformed

	// Talvez seria melhor criar um formulário separado para essas variaveis abaixo

	public JLabel getLblDestinatario() {
		return lblDestinatario;
	}

	public void setLblDestinatario(JLabel lblDestinatario) {
		this.lblDestinatario = lblDestinatario;
	}

	public JTextField getTxtMensagem() {
		return txtMensagem;
	}

	public void setTxtMensagem(JTextField txtMensagem) {
		this.txtMensagem = txtMensagem;
	}
	
	public ServerCommunication getServerCommunication() {
		return serverCommunication;
	}
	
	public void setServerCommunication(ServerCommunication serverCommunication) {
		this.serverCommunication = serverCommunication;
	}
	
	public Usuario getUsuario() {
		return usuario;
	}
	
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public static void main(String args[]) {
		/* Set the Nimbus look and feel */
		// <editor-fold defaultstate="collapsed" desc=" Look and feel setting code
		// (optional) ">
		/*
		 * If Nimbus (introduced in Java SE 6) is not available, stay with the default
		 * look and feel. For details see
		 * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
		 */
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(Chat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(Chat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(Chat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(Chat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
		// </editor-fold>

		/* Create and display the form */
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new Chat().setVisible(true);
			}
		});
	}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btEnviar;
    private javax.swing.JButton btSair;
    private javax.swing.JButton btnEnviarArquivo;
    private javax.swing.JScrollPane jMessageScrollPane;
    private javax.swing.JList<String> jMessagesList;
    private javax.swing.JList<String> jUserList;
    private javax.swing.JScrollPane jUserScrollPane;
    private javax.swing.JLabel lblConversandoCom;
    private javax.swing.JLabel lblDestinatario;
    private javax.swing.JLabel lblNomeUsuario;
    private javax.swing.JLabel lblOla;
    private javax.swing.JPanel paneBottomArea;
    private javax.swing.JPanel paneMensagemArea;
    private javax.swing.JPanel paneTopArea;
    private javax.swing.JTextField txtMensagem;
    // End of variables declaration//GEN-END:variables

	@Override
	public void onlineUser(String usuario) {
		listUserModule.addElement(usuario);
		listOpenChats.add(new ChatConversation(this.usuario.getLogin(), usuario));
	}

	@Override
	public void offlineUser(String usuario) {
		boolean elementoFoiRemovido = listUserModule.removeElement(usuario);
		if (!elementoFoiRemovido) {
			listUserModule.removeElement(usuario + CARACTER_NOTIFICADOR);
		}
	}

	@Override
	public void messageReceved(String usuario, String message) {
		// Se o Chat já estiver aberto
		if (getLblDestinatario().getText().replaceAll(CARACTER_NOTIFICADOR, "").equals(usuario)) {
			activeChat.addMessage(message);
			listChatMessageModule.addElement(message);
		} else {
			notificaClienteDeMensagemDeUsuario(usuario);
			for (ChatConversation chatConversation : listOpenChats) {
				if (chatConversation.getUsuarioDois().equals(usuario)) {
					chatConversation.addMessage(message);
					return;
				}
			}
			System.err.println("Não achei ninguém para notificar :c.\n Pedro Corrige isso!!!");
		}
		}
	
	@Override
	public void messageSent(String usuario, String status, String message) {
		System.out.println("Enviei uma mensagem pro usuário " + usuario + " que obteve o status " + status + " enviando a mensagem " + message);
		if (status.equals("sucesso")) {
			activeChat.addMessage(message);
			listChatMessageModule.addElement(message);
			return;
		}
		UserMessageNotifier.infoMessagePane(this, message);
	}
	
	@Override
	public void fileReceived(FileObjectData file) throws IOException {
		System.out.println("Recebi o arquivo: " + file.getFileName() +
				 "\nEnviado pelo " + file.getFileRemetente() + 
				 "\nPara o usuário " + file.getFileDestinatario());
		boolean showConfirmMessage = UserMessageNotifier.showConfirmMessage(this, "Você recebeu um arquivo de: " + file.getFileRemetente() +
																			"\nVocê deseja baixar o arquivo: " + file.getFileName() + "?");
		if (showConfirmMessage == true) {
			JFileChooser chooser = new JFileChooser();
	        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	        int browse = chooser.showOpenDialog(this);
	        if(browse == JFileChooser.APPROVE_OPTION){
	            String folder = chooser.getSelectedFile().toString() +"\\";
	    	    FileOutputStream outputStream = new FileOutputStream(folder + file.getFileName());
	    	    outputStream.write(file.getFile());
	    	    outputStream.close();
	        }
		}
	}
	
	/**
	 * Retorna o index do usuário da lista de UserList.<p>
	 * 
	 * Esté método pode ser útil quando você deseja manipular algum elementro da lista, mas precisa saber o ID do mesmo primeiro.
	 *
	 * @param usuario - O usuário no qual você deseja saber o ID.
	 * @return the int - Se Retornado -1. O Usuário não foi encontrado, caso contrário retornará o valor do index do mesmo.
	 */
	public int retornaUserIndexNaListaDeUsuarios(String usuario) {
		for (int i = 0; i < listUserModule.getSize(); i++) {
			if (isElementoSelecionadoUsuarioEspecificado(usuario, i)) {
				return i;
			}
		}
		return -1;
	}

	private boolean isElementoSelecionadoUsuarioEspecificado(String usuario, int i) {
		return removeNotificacaoNomeUsuario(listUserModule.getElementAt(i)).equals(usuario);
	}
	
	// FIXME Assuntos referentes a destinatário devem seguir para uma outra classe
	private void notificaClienteDeMensagemDeUsuario(String usuario) {
		int indexElemento = retornaUserIndexNaListaDeUsuarios(usuario);
		if ((indexElemento != -1) && !getLblDestinatario().getText().equals(usuario)) {
			listUserModule.set(indexElemento, usuario + CARACTER_NOTIFICADOR);
		}
	}
	
	private String removeNotificadorDeUsuarioSelecionado(String userEscolhido) {
		if (userEscolhido.contains(CARACTER_NOTIFICADOR)) {
			userEscolhido = removeNotificacaoNomeUsuario(userEscolhido);
//			int userIndex = retornaUserIndexNaListaDeUsuarios(userEscolhido);
			//listUserModule.set(userIndex, userEscolhido);
			// Atualiza na lista. Removendo o marcador do nome do Usuário selecionado
			listUserModule.set(jUserList.getSelectedIndex(), userEscolhido);
		}
		return userEscolhido;
	}
	
	private String removeNotificacaoNomeUsuario(String usuario) {
		return usuario.replace(CARACTER_NOTIFICADOR, "");
	}

}
