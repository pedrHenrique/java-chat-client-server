package br.unip.chatclient.util.notifier;

import java.awt.Component;

import javax.swing.JOptionPane;

public final class UserMessageNotifier {
	
	private UserMessageNotifier() {
		
	}

	/**
	 * Exibe uma simples JOptionPane de <b>ERROR</b> na tela que chamar este método.<br> 
	 * Contendo a mensagem informada.
	 *
	 * @param tela - A tela em que a mensagem deve aparecer.
	 * @param mensagem - A mensagem.
	 */
	public static void errorMessagePane(Component tela, String mensagem) {
		JOptionPane.showMessageDialog(tela, mensagem, "Mensagem de erro", JOptionPane.ERROR_MESSAGE);
	}
	
	/**
	 * Exibe uma simples JOptionPane de <b>INFO</b> na tela que chamar este método.<br> 
	 * Contendo a mensagem informada.
	 *
	 * @param tela - A tela em que a mensagem deve aparecer.
	 * @param mensagem - A mensagem.
	 */
	public static void infoMessagePane(Component tela, String mensagem) {
		JOptionPane.showMessageDialog(tela, mensagem, "Mensagem de erro", JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static boolean showConfirmMessage(Component tela, String mensagem) {
		int dialogResult = JOptionPane.showConfirmDialog(tela, mensagem,"Warning", JOptionPane.YES_NO_OPTION);
		return dialogResult == JOptionPane.YES_OPTION;
	}
	
}
