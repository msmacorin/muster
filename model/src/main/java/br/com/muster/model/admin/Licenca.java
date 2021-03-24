package br.com.muster.model.admin;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import br.com.muster.model.enums.ESituacao;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "licenca")
public class Licenca implements Serializable {

    private static final long serialVersionUID = -1181810700904958521L;

    public interface LicencaColumns {

        String id = "id";
        String inicio = "inicio";
        String prazo = "prazo";
        String situacao = "situacao";
        String validacao = "validacao";
        String empresa_nome = "empresa.getNome()";
    }

    @Id
    @Column(name = "id_licenca")
    @Type(type = "pg-uuid")
    private UUID id;

    @Column(name = "data_inicio")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date inicio;
    
    private Integer prazo;
    private String validacao;

    @Type(type = "br.com.muster.model.usertype.SituacaoUserType")
    private ESituacao situacao;

    @ManyToOne
    @JoinColumn(name = "id_empresa")
    private Empresa empresa;

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public ESituacao getSituacao() {
        return situacao;
    }

    public void setSituacao(ESituacao situacao) {
        this.situacao = situacao;
    }

    public Licenca() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Date getInicio() {
        return inicio;
    }

    public void setInicio(Date inicio) {
        this.inicio = inicio;
    }

    public Integer getPrazo() {
        return prazo;
    }

    public void setPrazo(Integer prazo) {
        this.prazo = prazo;
    }

    public String getValidacao() {
        return validacao;
    }

    public void setValidacao(String validacao) {
        this.validacao = validacao;
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
