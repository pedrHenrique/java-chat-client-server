package br.unip.chatserver.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Usuario {
	
	//@NOTNULL - Quando os ID estiverem no esquema
	@PositiveOrZero(message = "id não pode receber o valor '${validatedValue}'")
	private Long id;
	
	@NotBlank(message = "Campo nome não pode ser vazio")
	@Size(min = 3, max = 65, message = "Nome precisar estar entre {min} e {max} caracteres")
	@Pattern(regexp = "[a-zA-ZáéíóúâêîôûãõÁÉÍÓÚÂÊÎÔÛÃÕ ]+", message = "Nome só deve conter letras!!")
	private String nome;
	
	@NotBlank(message = "Campo login não pode ser vazio")
	@Size(min = 3, max = 30, message = "Login precisar estar entre {min} e {max} caracteres")
	@Pattern(regexp = "[\\w\\.\\-\\_!@#$%¨&*\\(\\)]+", message = "Esta forma de login não pode ser aceita.")
	private String login;
	
	@NotBlank(message = "Campo senha não pode ser vazio")
	@Size(min = 3, max = 30, message = "Senha precisar estar entre {min} e {max} caracteres")
	@Pattern(regexp = "[\\w\\.\\-\\_!@#$%¨&*\\(\\)]+", message = "Esta forma de senha não pode ser aceita.")
	private String password;
	
	public Usuario(Long id, String login, String password) {
			this.id = id;
			this.nome = "Exemplo de como ficará o nome";
			this.login = login.trim();
			this.password = password.trim();
	}
	
	public Usuario(Long id, String nome, String login, String password) {
		this.id = id;
		this.nome = nome.trim();
		this.login = login.trim();
		this.password = password.trim();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
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

	// TODO - Quando os ID's estiverem presentes no projeto, acredito que seria melhor mudar o equals/hashcode para o ID.
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
	
	public Usuario safeUserToString() {
		return new Usuario(getId(), getNome(), getLogin(), getPassword().replaceAll(".*", "#"));
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(safeUserToString(), ToStringStyle.JSON_STYLE);
	}

}
