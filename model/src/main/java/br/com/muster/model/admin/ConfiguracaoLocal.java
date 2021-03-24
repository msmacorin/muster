/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.muster.model.admin;

import br.com.muster.model.enums.ESimNao;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Type;

/**
 *
 * @author macorin
 */
@Entity
@Table(name = "configuracao_local")
public class ConfiguracaoLocal implements Serializable{
    
    private static final long serialVersionUID = -512730098274441749L;
    
    @Id
    @Column(name = "id_configuracao_local")
    private Long id;
    
    @Column(name = "e_mail")
    private String email;
    
    @Column(name = "url_api")
    private String urlApi;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_empresa")
    private Empresa empresa;
    
    @Type(type = "br.com.muster.model.usertype.SimNaoUserType")
    @Column(name = "executar_servicos")
    private ESimNao executarServicos;

    public ConfiguracaoLocal() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUrlApi() {
        return urlApi;
    }

    public void setUrlApi(String urlApi) {
        this.urlApi = urlApi;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public ESimNao getExecutarServicos() {
        return executarServicos;
    }

    public void setExecutarServicos(ESimNao executarServicos) {
        this.executarServicos = executarServicos;
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
