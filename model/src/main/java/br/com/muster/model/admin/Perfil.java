/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.muster.model.admin;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import br.com.muster.model.enums.ETipoPerfil;
import java.util.UUID;

/**
 *
 * @author macorin
 */
@Entity
@Table(name = "perfil")
public class Perfil implements Serializable {

    public interface PerfilColumns {

        String id = "id";
        String nome = "nome";
        String tipoPerfil = "tipoPerfil";
    }

    private static final long serialVersionUID = -7572929680701871088L;

    @Id
    @Column(name = "id_perfil")
    @Type(type = "pg-uuid")
    private UUID id;

    private String nome;

    @Column(name = "tipo_perfil")
    @Type(type = "br.com.muster.model.usertype.TipoPerfilUserType")
    private ETipoPerfil tipoPerfil;

    public Perfil() {
    }

    public Perfil(ETipoPerfil tipoPerfil) {
        this.tipoPerfil = tipoPerfil;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public ETipoPerfil getTipoPerfil() {
        return tipoPerfil;
    }

    public void setTipoPerfil(ETipoPerfil tipoPerfil) {
        this.tipoPerfil = tipoPerfil;
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
