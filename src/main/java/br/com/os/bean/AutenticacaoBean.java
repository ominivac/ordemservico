package br.com.os.bean;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;

import br.com.os.dao.UsuarioDAO;

import br.com.os.domain.Usuario;


@ManagedBean(name = "login")
@SessionScoped
public class AutenticacaoBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Usuario usuario;
	
	
	
	public Usuario getUsuario() {
		return usuario;
	}
	
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	
	
	@PostConstruct
	public void iniciar() {
		usuario = new Usuario();
	}
	
	
	public void login() {
		try {
			Faces.redirect("./pages/principal.xhtml");
		} catch (IOException e) {
			Messages.addGlobalError("Nome de usuário ou senha inválidas");
			e.printStackTrace();
		}
	}
	
	
	public void autenticar() {
		try {
			UsuarioDAO udao = new UsuarioDAO();
			usuario =  udao.login(usuario.getNome() , usuario.getSenhaSemCripto() );
			if(usuario == null) {
				//Messages.addGlobalError("Nome de usuário ou senha inválidas" );
				FacesContext.getCurrentInstance().
					addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
				    "Nome de usuário ou senha inválidos !" ,"Nome de usuário ou senha inválidos !"));
				Messages.addGlobalInfo("Nome de usuário ou senha inválidas");
				Faces.redirect("./pages/autenticacao.xhtml");
				//System.out.println("foiiiiii");
			}
			
			Faces.redirect("./pages/principal.xhtml");
			
		} catch (IOException e) {
			Messages.addGlobalError(e.getMessage() );
		}
		
	}
	
	public boolean ehadmin() {
		return usuario.getRole().toString().equals("A");
	}
	
	public void sair() {
		System.out.println("entrou em sair");
		usuario = null;
		try {
			Faces.redirect("./pages/autenticacao.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public boolean temPermisssoes(List<String> permissoes) {
		
		for(String permissao : permissoes) {
			if(usuario.getRole().toString().equals(permissao)) {
				return true;
			}
		}
		
		return false;
	}
	
	
	
}
