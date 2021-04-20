package br.unip.chatserver.model;

import java.sql.Timestamp;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class ChatMessage {
	
	@Valid
	private Usuario remetente;
	
	@Valid
	private Usuario destinatario;
	
	@NotNull
	private Timestamp data;
	
	@NotNull
	private String mensagem;
	
	public ChatMessage(@Valid Usuario remetente, Timestamp data, String mensagem, @Valid Usuario destinatario) {
		this.remetente = remetente;
		this.data = data;
		this.mensagem = mensagem;
		this.destinatario = destinatario;
	}
	
	public ChatMessage(@Valid Usuario remetente, String mensagem, @Valid Usuario destinatario) {
		this.remetente = remetente;
		this.data = new Timestamp(System.currentTimeMillis());//DateTime.now();
		this.mensagem = mensagem;
		this.destinatario = destinatario;
	}

	public Usuario getRemetente() {
		return remetente;
	}

	public void setRemetente(Usuario remetente) {
		this.remetente = remetente;
	}

	public Usuario getDestinatario() {
		return destinatario;
	}

	public void setDestinatario(Usuario destinatario) {
		this.destinatario = destinatario;
	}

	public Timestamp getData() {
		return data;
	}

	public void setData(Timestamp data) {
		this.data = data;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((destinatario == null) ? 0 : destinatario.hashCode());
		result = prime * result + ((mensagem == null) ? 0 : mensagem.hashCode());
		result = prime * result + ((remetente == null) ? 0 : remetente.hashCode());
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
		ChatMessage other = (ChatMessage) obj;
		if (data == null) {
			if (other.data != null) {
				return false;
			}
		} else if (!data.equals(other.data)) {
			return false;
		}
		if (destinatario == null) {
			if (other.destinatario != null) {
				return false;
			}
		} else if (!destinatario.equals(other.destinatario)) {
			return false;
		}
		if (mensagem == null) {
			if (other.mensagem != null) {
				return false;
			}
		} else if (!mensagem.equals(other.mensagem)) {
			return false;
		}
		if (remetente == null) {
			if (other.remetente != null) {
				return false;
			}
		} else if (!remetente.equals(other.remetente)) {
			return false;
		}
		return true;
	}

	@Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE).append(data.toLocalDateTime().getHour() + ":" + data.toLocalDateTime().getMinute() + " ")
        								.append(remetente.getLogin() + ": ")
        								.append(mensagem + "\n").toString();
    }	

}
