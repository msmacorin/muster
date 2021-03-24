/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.muster.client.admin;

import br.com.muster.client.AbstractBean;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;

/**
 *
 * @author macorin
 */
@ManagedBean
@RequestScoped
public class HomeBean extends AbstractBean {

    private String mensagem;

    @Inject
    private EmpresaSelecionadaBean empresaSelecionada;

    @PostConstruct
    public void init() {
        if (empresaSelecionada.necessitaSelecionarEmpresa()) {
            mensagem = getString("selecionar_empresa");
        } else {
            mensagem = getString("boas_vindas");
        }
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}
