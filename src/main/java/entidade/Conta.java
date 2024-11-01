package entidade;

import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Persistence;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "conta")
public class Conta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "valor_operacao")
	private Double valorOperacao;
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataTransacao;
	@Column(length = 150, name = "descricao", nullable = true, unique = false)
	private String descricao;
	private String tipoTransacao;
	private String nomeCorrentista;
	@Column(name = "cpfCorrentista", unique = true)
	private String cpfCorrentista;
	private BigInteger saldo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getValorOperacao() {
		return valorOperacao;
	}

	public void setValorOperacao(Double valorOperacao) {
		this.valorOperacao = valorOperacao;
	}

	public Date getDataTransacao() {
		return dataTransacao;
	}

	public void setDataTransacao(Date dataTransacao) {
		this.dataTransacao = dataTransacao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getTipoTransacao() {
		return tipoTransacao;
	}

	public void setTipoTransacao(String tipoTransacao) {
		this.tipoTransacao = tipoTransacao;
	}

	public String getNomeCorrentista() {
		return nomeCorrentista;
	}

	public void setNomeCorrentista(String nomeCorrentista) {
		this.nomeCorrentista = nomeCorrentista;
	}

	public String getCpfCorrentista() {
		return cpfCorrentista;
	}

	public void setCpfCorrentista(String cpfCorrentista) {
		this.cpfCorrentista = cpfCorrentista;
	}

	public BigInteger getSaldo() {
		return saldo;
	}

	public void setSaldo(BigInteger saldo) {
		this.saldo = saldo;
	}

	@Override
	public String toString() {
		return "Conta [id=" + id + ", valorOperacao=" + valorOperacao + ", dataTransacao=" + dataTransacao
				+ ", descricao=" + descricao + ", tipoTransacao=" + tipoTransacao + ", nomeCorrentista="
				+ nomeCorrentista + ", cpfCorrentista=" + cpfCorrentista + "]";
	}

}