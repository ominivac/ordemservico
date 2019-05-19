package br.com.os.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.os.converter.SampleEntity;

@Entity
@Table(name="os")

@NamedQueries({
	@NamedQuery(name="OS.listar", query= "SELECT os FROM OS os"),
	@NamedQuery(name="OS.listarByDateDesc", query= "SELECT os FROM OS os ORDER BY os.dataSolicitacao ASC"),
	@NamedQuery(name="OS.buscarPorCodigo", query= "SELECT os FROM OS os WHERE os.codigoOS = :codigo" ),
	@NamedQuery(name="OS.buscarPorServico", query= "SELECT os FROM OS os WHERE os.atividade LIKE :atividade" ),	
	@NamedQuery(name="OS.buscarEntreDatas", query= "SELECT os FROM OS os WHERE os.dataSolicitacao BETWEEN :data_inicial AND :data_final" )
})
public class OS implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "cod_os", columnDefinition= "serial", unique=true, nullable=false)
	@GeneratedValue(strategy= GenerationType.IDENTITY )
	private Integer codigoOS;
	
	@OneToMany(fetch=FetchType.EAGER , mappedBy="os")
	private List<Item> itensOs;

	
	@Temporal(TemporalType.DATE)
	@Column(name="data_solicitacao", nullable = true)
	private Date dataSolicitacao;
	
	
	@Temporal(TemporalType.DATE)
	@Column(name="data_previsao", nullable = true)
	private Date dataPrevisaoEntrega;
	
	@Column(name = "valor_total", precision = 8, scale = 2)
	private BigDecimal valorTotal;
	
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="usuario_cod",referencedColumnName="cod_usuario",columnDefinition="integer", nullable = true)
	//a opcao columnDefinition � necessaria, caso contrario o campo sera criado como not null. bug versao hibernate?
	// https://stackoverflow.com/questions/15511899/cannot-make-manytoone-relationship-nullable / https://hibernate.atlassian.net/browse/HHH-8229
	private Usuario usuario;
	
	
	
	
	@Column(name="aberta", nullable = true)
	private Boolean aberta;
	
	
	@Column(name="atividade", length=255,  nullable = true)
	private String  atividade;

	
	public Integer getCodigoOS() {
		return codigoOS;
	}
	
	public void setCodigoOS(Integer codigo) {
		this.codigoOS = codigo;
	}
	
	public String getCodigoFormatado() {
		
		if(codigoOS <10) {
			return "2018.094-00" + codigoOS;
		}
		
		if(codigoOS >=10 && codigoOS < 100) {
			return "2018.094-0" + codigoOS;
		}
		
		if(codigoOS >=100) {
			return "2018.094-"+codigoOS.toString();
		}
		
		return "";
	}
	
	
	public Integer getTotalHoras() {
		Integer totalHoras = 0;
		
		if(this.itensOs.isEmpty() ) {
			return totalHoras;
		}else {
			for(Item item : itensOs) {
				totalHoras += item.getQuantidadeHoras();
				return totalHoras;
			}
		}
		return totalHoras;
	}
	
	

	
	
		
	public String getAtividade() {
		return atividade;
	}

	public void setAtividade(String atividade) {
		this.atividade = atividade;
	}

	public List<Item> getItensOs() {
		return itensOs;
	}
	
	
	public Boolean getAberta() {
		return aberta;
	}

	public void setAberta(Boolean aberta) {
		this.aberta = aberta;
	}

	public void setItensOs(List<Item> itens) {
		itensOs = new ArrayList<Item>();
		itensOs = itens;
	}
	
	
	

	public Date getDataSolicitacao() {
		return dataSolicitacao;
	}

	public void setDataSolicitacao(Date dataSolicitacao) {
		this.dataSolicitacao = dataSolicitacao;
	}

	public Date getDataPrevisaoEntrega() {
		return dataPrevisaoEntrega;
	}

	public void setDataPrevisaoEntrega(Date dataPrevisaoEntrega) {
		this.dataPrevisaoEntrega = dataPrevisaoEntrega;
	}

	public BigDecimal getValorTotal() {
		if(itensOs.isEmpty() ) {
			valorTotal = new BigDecimal("0.00");
		}else {
			valorTotal = new BigDecimal("0.00");
			
			for(int posicao = 0 ; posicao < itensOs.size(); posicao++) {
				Item item = itensOs.get(posicao);
				valorTotal = valorTotal.add(item.getValorParcial() )  ;
			}
		}
		
		return valorTotal;
	}

	public void setValorTotal( BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	


	@Override
	public String toString() {
		return "OS [codigoOS=" + codigoOS + "\n, itensOs=" + itensOs + ", \n dataSolicitacao=" + dataSolicitacao
				+ ", dataPrevisaoEntrega=" + dataPrevisaoEntrega + ", valorTotal=" + valorTotal + ", usuario=" + usuario
				+ ", aberta=" + aberta + ", atividade=" + atividade + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigoOS == null) ? 0 : codigoOS.hashCode());
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
		OS other = (OS) obj;
		if (codigoOS == null) {
			if (other.codigoOS != null)
				return false;
		} else if (!codigoOS.equals(other.codigoOS))
			return false;
		return true;
	}
	
	
	
	
}
