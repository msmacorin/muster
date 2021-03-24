/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.muster.model.servico;

import br.com.muster.model.admin.Empresa;
import br.com.muster.model.enums.EEnvio;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Type;

/**
 *
 * @author macorin
 */
@Entity
@Table(name = "hit_ratio_library_cache")
public class HitRatioLibraryCache implements Serializable {

    private static final long serialVersionUID = 8574003892118708522L;

    @Id
    @Column(name = "id_hit_ratio_library_cache")
    @Type(type = "pg-uuid")
    private UUID id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date data;

    private BigDecimal resultado;

    @Type(type = "br.com.muster.model.usertype.EnvioUserType")
    private EEnvio envio;

    @ManyToOne
    @JoinColumn(name = "id_empresa")
    private Empresa empresa;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public BigDecimal getResultado() {
        return resultado;
    }

    public void setResultado(BigDecimal resultado) {
        this.resultado = resultado;
    }

    public EEnvio getEnvio() {
        return envio;
    }

    public void setEnvio(EEnvio envio) {
        this.envio = envio;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
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
