/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.muster.model.admin;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;
import org.hibernate.annotations.Type;

/**
 *
 * @author macorin
 */
@Entity
@Table(name = "usuario")
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1246305392366359540L;

    public interface UsuarioColumns {

        String id = "id";
        String login = "login";
        String senha = "senha";
        String nome = "nome";
        String email = "email";
    }

    @Id
    @Column(name = "id_usuario")
    @Type(type = "pg-uuid")
    private UUID id;

    private String login;
    private String senha;
    private String nome;

    @Column(name = "e_mail")
    private String email;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "usuario_perfil",
            joinColumns = {
                @JoinColumn(name = "id_usuario", nullable = false, updatable = false)},
            inverseJoinColumns = {
                @JoinColumn(name = "id_perfil",
                        nullable = false, updatable = false)})
    private Set<Perfil> perfis;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "usuario_empresa",
            joinColumns = {
                @JoinColumn(name = "id_usuario", nullable = false, updatable = false)},
            inverseJoinColumns = {
                @JoinColumn(name = "id_empresa",
                        nullable = false, updatable = false)})
    private Set<Empresa> empresas;

    public Usuario() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Set<Perfil> getPerfis() {
        return perfis;
    }

    public void setPerfis(Set<Perfil> perfis) {
        this.perfis = perfis;
    }

    public void addPerfil(Perfil perfil) {
        if (perfis == null) {
            perfis = new LinkedHashSet<>();
        }

        perfis.add(perfil);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Empresa> getEmpresas() {
        return empresas;
    }

    public void setEmpresas(Set<Empresa> empresas) {
        this.empresas = empresas;
    }

    public String getUsuarioFormatado() {
        return String.format("%s - %s", nome, email);
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
