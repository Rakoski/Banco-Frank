package entidade;

import java.math.BigInteger;
import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name = "conta")
public class Conta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "valor_operacao")
	private Double valorOperacao;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_transacao")
	private Date dataTransacao;
	@Column(length = 150, name = "descricao", nullable = true, unique = false)
	private String descricao;
	@Column(name = "tipo_transacao")
	private String tipoTransacao;

	@ManyToOne
	@JoinColumn(name = "cliente_id")
	private Cliente cliente;

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

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	@Override
	public String toString() {
		return "Conta{" +
				"id=" + id +
				", valorOperacao=" + valorOperacao +
				", dataTransacao=" + dataTransacao +
				", descricao='" + descricao + '\'' +
				", tipoTransacao='" + tipoTransacao + '\'' +
				", cliente=" + cliente +
				'}';
	}
}
