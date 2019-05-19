package br.com.os.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.com.os.dao.ProdutoOsDAO;
import br.com.os.dao.UsuarioDAO;
import br.com.os.domain.ProdutoOS;
import br.com.os.domain.Usuario;

@ManagedBean
@ViewScoped
public class UsuarioBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Usuario usuario;
	private List<Usuario> usuarios ;
	
	
	
	
	
	public Usuario getUsuario() {
		return usuario;
	}
	
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	
	
	public List<Usuario> getUsuarios() {
		return usuarios;
	}
	
	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
	
	public void novo() {
		usuario = new Usuario();
	}
	
	
	
	@PostConstruct
	public void listar() {
		try {
			UsuarioDAO udao = new UsuarioDAO();
			usuarios =  udao.listar();
		}catch (RuntimeException e) {
			Messages.addGlobalError("Erro ao listar usu�rios");
			e.printStackTrace();
		}
	}
	
	
	
	
	public void salvar() {
		System.out.println("usuario em salvar " + usuario);
		try {
			UsuarioDAO usuarioDAO = new UsuarioDAO();
			//usuarioDAO.merge(usuario);
			usuarioDAO.salvar(usuario);
			usuario = new Usuario();
			usuarios = usuarioDAO.listar();
			
			Messages.addGlobalInfo("Usu�rio salvo com sucesso");
			
		}catch (RuntimeException e) {
			Messages.addGlobalError("Erro ao salvar usu�rio");
			e.printStackTrace();
		}
	}
	
	public void excluir(ActionEvent evento) {
		try {
			usuario = (Usuario) evento.getComponent().getAttributes().get("usuarioSelecionado");

			UsuarioDAO udao = new UsuarioDAO();
			udao.excluir(usuario);
			
			usuarios = udao.listar();
			
			Messages.addGlobalInfo("Usu�rio exclu�do com sucesso");

		} catch (RuntimeException e) {
			Messages.addGlobalError("Erro ao excluir usu�rio");
			e.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {
		try {
			usuario = (Usuario) evento.getComponent().getAttributes().get("usuarioSelecionado");
			System.out.println("usuario a editar: " + usuario);
			
			UsuarioDAO udao = new UsuarioDAO();
			udao.editar(usuario);
			//udao.merge(usuario);
			
			usuarios = udao.listar();

			Messages.addGlobalInfo("Usu�rio editado com sucesso !");

			// Messages.addGlobalInfo("resp selecionado: " + responsavelOS.getNome());
		} catch (RuntimeException e) {
			Messages.addGlobalError("Erro ao editar usu�rio !");
			e.printStackTrace();
		}
	}
	
	
	
	
	
}
