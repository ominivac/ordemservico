package br.com.os.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;



@SuppressWarnings("serial")
@Entity()
@Table(name="usuario")
@NamedQueries({
	@NamedQuery(name="Usuario.listar", query= "SELECT usuario FROM Usuario usuario"),
	@NamedQuery(name="Usuario.buscarPorCodigo", query= "SELECT usuario FROM Usuario usuario WHERE usuario.codigoUsuario = :codigo" ),
	@NamedQuery(name="Usuario.buscarPorEmail", query= "SELECT usuario FROM Usuario usuario WHERE usuario.email = :email" ),
	@NamedQuery(name="Usuario.login", query= "SELECT usuario FROM Usuario usuario WHERE usuario.email =:email AND usuario.senha =:senha" )
})
//@SequenceGenerator(name="generator_usuario",sequenceName="usuario_id_usuario_seq", allocationSize=1)
public class Usuario  implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	//@GeneratedValue(generator="generator_usuario")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "codigo_usuario")
	private Integer codigoUsuario;

	

	public Integer getCodigoUsuario() {
		return codigoUsuario;
	}

	public void setCodigoUsuario(Integer codigoUsuario) {
		this.codigoUsuario = codigoUsuario;
	}

	@Column(length=100, nullable = false)
	private String nome;
	@Column(length=32, nullable = false)
	private String senha;
	
	@Column(length=32, nullable = true)
	private String email;
	
	
	@Transient
	private String senhaSemCripto;

	@Enumerated(EnumType.STRING)
	private Role role;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	public String getSenhaSemCripto() {
		return senhaSemCripto;
	}
	
	public void setSenhaSemCripto(String senhaSemCripto) {
		this.senhaSemCripto = senhaSemCripto;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	

	@Override
	public String toString() {
		return "Usuario [codigoUsuario=" + codigoUsuario + ", nome=" + nome + ", senha=" + senha + ", email=" + email
				+ ", senhaSemCripto=" + senhaSemCripto + ", role=" + role + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigoUsuario == null) ? 0 : codigoUsuario.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (codigoUsuario == null) {
			if (other.codigoUsuario != null)
				return false;
		} else if (!codigoUsuario.equals(other.codigoUsuario))
			return false;
		return true;
	}

	
	
	
}
