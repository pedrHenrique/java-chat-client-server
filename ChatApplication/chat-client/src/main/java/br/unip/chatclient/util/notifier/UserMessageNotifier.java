package br.unip.chatclient.util.notifier;

import java.awt.Component;

import javax.swing.JOptionPane;

public final class UserMessageNotifier {
	
	private UserMessageNotifier() {
		
	}

	public static void errorMessagePane(Component tela, String mensagem) {
		JOptionPane.showMessageDialog(tela, mensagem, "Mensagem de erro", JOptionPane.ERROR_MESSAGE);
	}
	
}
