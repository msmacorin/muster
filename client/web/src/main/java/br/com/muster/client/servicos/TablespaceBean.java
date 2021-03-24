/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.muster.client.servicos;

import br.com.muster.client.AbstractBean;
import br.com.muster.facade.servico.TablespaceFacade;
import br.com.muster.model.admin.Empresa;
import br.com.muster.model.admin.ParametroSistema;
import br.com.muster.model.servico.Tablespace;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author macorin
 */
@ManagedBean
@ViewScoped
public class TablespaceBean extends AbstractBean {
        
    private Date inicio;
    private Date fim;
    
    private List<Tablespace> registros;
    
    @EJB
    private TablespaceFacade tablespaceFacade;

    public TablespaceBean() {
        inicio = new Date();
        fim = new Date();
        
        registros = new ArrayList<>();
    }
    
    public void selecionar() {
        Empresa empresa = session.getObjectSession(ParametroSistema.EMPRESA_SESSION);
        if (empresa == null) {
            messages(new FacesMessage(getString("selecionar_empresa_erro")));
        } else {
            registros = tablespaceFacade.selecionarPorPeriodo(empresa.getId(), inicio, fim);
        }
    }
    
    public Date getInicio() {
        return inicio;
    }

    public void setInicio(Date inicio) {
        this.inicio = inicio;
    }

    public Date getFim() {
        return fim;
    }

    public void setFim(Date fim) {
        this.fim = fim;
    }

    public List<Tablespace> getRegistros() {
        return registros;
    }

    public void setRegistros(List<Tablespace> registros) {
        this.registros = registros;
    }
}
