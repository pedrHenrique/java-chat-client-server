package br.unip.chatserver.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Usuario {
	
	@PositiveOrZero(message = "id não pode receber o valor '${validatedValue}'")
	private Long id;
	
	@NotNull(message = "Campo login não pode ser nulo")
	@Size(min = 3, max = 14, message = "Login '${validatedValue}' não pode ser aceito. Seu tamanho precisar estar entre {min} e {max}")
	//TODO Informar um pattern para impedir a inclussão de espaços na senha.
	private String login;
	
	@NotNull(message = "Campo password não pode ser nulo")
	@Size(min = 3, max = 26, message = "Senha '${validatedValue}' não pode ser aceita. Seu tamanho precisar estar entre {min} e {max}")
	//TODO Informar um pattern para impedir a inclussão de espaços na senha.
	private String password;
	
	public Usuario(Long id, String login, String password) {
		this.id = id;
		this.login = login;
		this.password = password;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((login == null) ? 0 : login.hashCode());
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
		Usuario other = (Usuario) obj;
		if (login == null) {
			if (other.login != null) {
				return false;
			}
		} else if (!login.equals(other.login)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
