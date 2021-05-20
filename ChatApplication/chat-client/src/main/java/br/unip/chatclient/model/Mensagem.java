package br.unip.chatclient.model;

/**
 *
 * @author Erick
 */
public class Mensagem {
    private int id;
    private String mensagem;
    private String Destinatario;
    private String Remetente;

    public String getDestinatario() {
        return Destinatario;
    }

    public void setDestinatario(String Destinatario) {
        this.Destinatario = Destinatario;
    }

    public String getRemetente() {
        return Remetente;
    }

    public void setRemetente(String Remetente) {
        this.Remetente = Remetente;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
     
}
