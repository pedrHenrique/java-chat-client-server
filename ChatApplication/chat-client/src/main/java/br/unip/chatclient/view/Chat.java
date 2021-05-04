/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unip.chatclient.view;

import br.unip.chatclient.model.ServerCommunication;
import br.unip.chatclient.model.ServerEvents;
import br.unip.chatclient.model.ServerListener;
import br.unip.chatclient.util.notifier.UserMessageNotifier;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.ListModel;

public class Chat extends JFrame implements ServerEvents{

	/*
	 * Basicamente o que temos agora!!
	 * 
	 * Sempre que um usuário logar, ele será redirecionado a esse tela de Chat. A
	 * tela de chat atualmente consegue pegar a lista de usuários logados NO MOMENTO
	 * QUE O USUÁRIO ESPECÍFICO ENTROU NESTA TELA.
	 * 
	 * Ou seja, se um usuário novo entrar ou sair, a lista não vai se atualizar
	 * automaticamente... Ainda não.
	 * 
	 * 
	 * Requisições manuais quebram a thread principal
	 */

	private ServerCommunication serverCommunication;

	private String usuario;
	
	private DefaultListModel<String> listUserModule;
	
	// Para adicionar elementos na lista de mensagem. Nome destaVariavel.addElement(StringQualquer)
	private DefaultListModel<String> listChatMessageModule;

	public Chat(ServerCommunication serverCommunication) throws IOException {
		initComponents();
		this.serverCommunication = serverCommunication;
		this.usuario = serverCommunication.retornaUsuario();
		this.defineUsuarioNaTela();
		this.preencheUserList(serverCommunication);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE); // TODO - Até descobrir como escolher customizar a forma de
															// fechar o form. O botão de fechar vai ficar "desativado".
		this.paneMensagemArea.setVisible(false);
		this.setVisible(true);
		new ServerListener(this, serverCommunication.getConnection()).start();
	}

	private void defineUsuarioNaTela() {
		this.lblNomeUsuario.setText(usuario);
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
		for (String usuario : listUsuariosOnline) {
			listUserModule.addElement(usuario);
		}
	}

	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jUserScrollPane = new javax.swing.JScrollPane();
        listUserModule = new DefaultListModel<String>();
        jUserList = new javax.swing.JList<>(listUserModule);
        paneBottomArea = new javax.swing.JPanel();
        txtMensagem = new javax.swing.JTextField();
        btEnviar = new javax.swing.JButton();
        btnRecarregar = new javax.swing.JButton();
        paneTopArea = new javax.swing.JPanel();
        btSair = new javax.swing.JButton();
        lblOla = new javax.swing.JLabel();
        lblNomeUsuario = new javax.swing.JLabel();
        paneMensagemArea = new javax.swing.JPanel();
        jMessageScrollPane = new javax.swing.JScrollPane();
        listChatMessageModule = new DefaultListModel<String>();
        jMessagesList = new javax.swing.JList<>(listChatMessageModule);
        lblConversandoCom = new javax.swing.JLabel();
        lblDestinatario = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jUserList.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jUserList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
            	aoSelecionarUmUsuario(evt);
            }
        });
        jUserScrollPane.setViewportView(jUserList);

        paneBottomArea.setBackground(new java.awt.Color(204, 204, 204));

        txtMensagem.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        btEnviar.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        btEnviar.setText("Enviar");
        btEnviar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btEnviarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout paneBottomAreaLayout = new javax.swing.GroupLayout(paneBottomArea);
        paneBottomArea.setLayout(paneBottomAreaLayout);
        paneBottomAreaLayout.setHorizontalGroup(
            paneBottomAreaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paneBottomAreaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtMensagem, javax.swing.GroupLayout.DEFAULT_SIZE, 445, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(btEnviar)
                .addContainerGap())
        );
        paneBottomAreaLayout.setVerticalGroup(
            paneBottomAreaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paneBottomAreaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(paneBottomAreaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtMensagem, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btEnviar))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        btnRecarregar.setText("Recarregar");
        btnRecarregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRecarregarActionPerformed(evt);
            }
        });

        paneTopArea.setBackground(new java.awt.Color(204, 204, 204));

        btSair.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        btSair.setText("Sair");
        btSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSairActionPerformed(evt);
            }
        });

        lblOla.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblOla.setText("Olá ");

        lblNomeUsuario.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

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

        lblConversandoCom.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        lblConversandoCom.setText("Conversando com: ");

        lblDestinatario.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        javax.swing.GroupLayout paneMensagemAreaLayout = new javax.swing.GroupLayout(paneMensagemArea);
        paneMensagemArea.setLayout(paneMensagemAreaLayout);
        paneMensagemAreaLayout.setHorizontalGroup(
            paneMensagemAreaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paneMensagemAreaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(paneMensagemAreaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jMessageScrollPane)
                    .addGroup(paneMensagemAreaLayout.createSequentialGroup()
                        .addComponent(lblConversandoCom)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblDestinatario)
                        .addGap(0, 434, Short.MAX_VALUE)))
                .addContainerGap())
        );
        paneMensagemAreaLayout.setVerticalGroup(
            paneMensagemAreaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, paneMensagemAreaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(paneMensagemAreaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblConversandoCom)
                    .addComponent(lblDestinatario))
                .addGap(18, 18, 18)
                .addComponent(jMessageScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 243, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jUserScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRecarregar))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(paneBottomArea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(paneMensagemArea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
            .addComponent(paneTopArea, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(paneTopArea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jUserScrollPane)
                            .addGap(0, 0, 0)
                            .addComponent(btnRecarregar))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(344, 344, 344)
                            .addComponent(paneBottomArea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(0, 0, Short.MAX_VALUE)))
                    .addComponent(paneMensagemArea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

	private void btSairActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btSairActionPerformed
		try {
			this.serverCommunication.doLogoff();
			System.exit(0);
		} catch (IOException ex) {
			// Logger.getLogger(Chat.class.getName()).log(Level.SEVERE, null, ex);
			UserMessageNotifier.errorMessagePane(this, ex.getMessage());
		}
	}// GEN-LAST:event_btSairActionPerformed

	private void aoSelecionarUmUsuario(MouseEvent evt) {// GEN-FIRST:event_jUserListMouseClicked
		if (evt.getClickCount() > 1) {
			String login = jUserList.getSelectedValue();
			// MessagePane messagePane = new MessagePane(client, login)
			// JFrame f = new JFrame("Message: " + login);
			// f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			// f.setSize(500, 500);
			// f.getContentPane().add(messagePane, BorderLayout.CENTER);
			// f.setVisible(true);
			this.paneMensagemArea.setVisible(true);
			this.paneMensagemArea.setEnabled(true);
			this.lblDestinatario.setText(login);
		}
	}// GEN-LAST:event_jUserListMouseClicked

	private void btnRecarregarActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnRecarregarActionPerformed
		this.preencheUserList(serverCommunication);
	}// GEN-LAST:event_btnRecarregarActionPerformed

	private void btEnviarActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btEnviarActionPerformed
		try {
			// Envia requisição para o server
			String destinatario = getLblDestinatario().getText();
			String mensagem = txtMensagem.getText();
			serverCommunication.doMensagem(destinatario, mensagem);
			
			// Adicionando a mensagem na tela
			listChatMessageModule.addElement(mensagem);
			
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
				try {
					// TODO REMOVER DEPOIS!
					new Chat(null).setVisible(true);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btEnviar;
    private javax.swing.JButton btSair;
    private javax.swing.JButton btnRecarregar;
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
	}

	@Override
	public void offlineUser(String usuario) {
		listUserModule.removeElement(usuario);
	}
	
	public ServerCommunication getServerCommunication() {
		return serverCommunication;
	}
	
	public void setServerCommunication(ServerCommunication serverCommunication) {
		this.serverCommunication = serverCommunication;
	}
}
