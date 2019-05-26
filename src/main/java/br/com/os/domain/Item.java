package br.com.os.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;




@Entity
@Table(name="item")
@NamedQueries({
	@NamedQuery(name="Item.listar", query= "SELECT item FROM Item item"),
	@NamedQuery(name="Item.buscarPorCodigoItem", query= "SELECT item FROM Item item WHERE item.codigoItem = :codigo" ),
	@NamedQuery(name="Item.buscarPorCodOsAndCodProduto", query= "SELECT i FROM Item i WHERE i.os.codigoOS = :codOS AND i.codigoItem = :codItem" )
})
@SequenceGenerator(name="generator_item",sequenceName="item_id_item_seq", allocationSize=1)
public class Item implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(generator="generator_item")
	@Column(name = "codigo_item")
	private Long codigoItem;

	
	
	public Long getCodigoItem() {
		return codigoItem;
	}

	public void setCodigoItem(Long codigoItem) {
		this.codigoItem = codigoItem;
	}

	@Column(name="quantidade", nullable = false)
	private Integer quantidade;
	
	
	@Column(name="atividadeItem", length=255,  nullable = true)
	private String  atividadeItem;
	
	@Temporal(TemporalType.DATE)
	@Column(name="data_entrega", nullable = true)
	private Date dataEntrega;
	
	@Column(name="quantidade_horas", nullable = true)
	private Integer quantidadeHoras;
	
	@Column(name="aberto", nullable = true)
	private Boolean aberto;
	
	
	@Column(name="valor_parcial", nullable = false, precision = 8, scale = 2)
	private BigDecimal valorParcial;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="produto_cod",  referencedColumnName="codigo_produto", nullable = false)
	private ProdutoOS produtoOS;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="os_cod", referencedColumnName="codigo_os",  nullable = false)
	private OS os;

	

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
	
	
	public Integer getQuantidadeHoras() {
		return quantidadeHoras;
	}
	
	public void setQuantidadeHoras(Integer quantidadeHoras) {
		this.quantidadeHoras = quantidadeHoras;
	}
	
	
	
	public String getAtividadeItem() {
		return atividadeItem;
	}

	public void setAtividadeItem(String atividadeItem) {
		this.atividadeItem = atividadeItem;
	}

	public Date getDataEntrega() {
		return dataEntrega;
	}

	public void setDataEntrega(Date dataEntrega) {
		this.dataEntrega = dataEntrega;
	}

	public Boolean getAberto() {
		return aberto;
	}

	public void setAberto(Boolean aberto) {
		this.aberto = aberto;
	}

	public BigDecimal getValorParcial() {
		valorParcial =  produtoOS.getValorPorHora().multiply(new BigDecimal( quantidade)).multiply( new BigDecimal(this.quantidadeHoras));
		return valorParcial;
	}

	

	public void setValorParcial() {
		
	}

	public ProdutoOS getProdutoOS() {
		return produtoOS;
	}

	public void setProdutoOS(ProdutoOS produtoOS) {
		this.produtoOS = produtoOS;
	}

	public OS getOs() {
		return os;
	}

	public void setOs(OS os) {
		this.os = os;
	}

	
	
	/*
	@Override
	public String toString() {
		return "Item [codigoItem=" + codigoItem + ",Atividade="+ atividadeItem + " ,quantidade=" + quantidade + ", quantidadeHoras=" + quantidadeHoras
				+ ", valorParcial=" + valorParcial + ", produtoOS=" + produtoOS + ", os=" + os + "]";
	}*/
	
	
	
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigoItem == null) ? 0 : codigoItem.hashCode());
		return result;
	}

	@Override
	public String toString() {
		return "Item [codigoItem=" + codigoItem + ", quantidade=" + quantidade + ", atividadeItem=" + atividadeItem
				+ ", dataEntrega=" + dataEntrega + ", quantidadeHoras=" + quantidadeHoras + ", aberto=" + aberto
				+ ", valorParcial=" + valorParcial + ", produtoOS=" + produtoOS + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Item other = (Item) obj;
		if (codigoItem == null) {
			if (other.codigoItem != null)
				return false;
		} else if (!codigoItem.equals(other.codigoItem))
			return false;
		return true;
	}

	

	

	
	
	
	
	
	
}
