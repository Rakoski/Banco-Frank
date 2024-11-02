package entidade;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "cliente")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomeCorrentista;
    @Column(name = "cpfCorrentista", unique = true, nullable = false)
    private String cpfCorrentista;
    private BigInteger saldo;
    @OneToMany
    private List<Conta> contas;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        return "Cliente{" +
                "id=" + id +
                ", nomeCorrentista='" + nomeCorrentista + '\'' +
                ", cpfCorrentista='" + cpfCorrentista + '\'' +
                ", saldo=" + saldo +
                '}';
    }
}
