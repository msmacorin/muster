/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.muster.client.servicos;

import br.com.muster.client.AbstractBean;
import br.com.muster.facade.servico.HitRatioBufferCacheFacade;
import br.com.muster.model.admin.Empresa;
import br.com.muster.model.admin.ParametroSistema;
import br.com.muster.model.servico.HitRatioBufferCache;
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
public class HitRatioBufferCacheBean extends AbstractBean {

    private Date inicio;
    private Date fim;

    private List<HitRatioBufferCache> registros;

    @EJB
    private HitRatioBufferCacheFacade hitRatioBufferCacheFacade;

    public HitRatioBufferCacheBean() {
        inicio = new Date();
        fim = new Date();
        
        registros = new ArrayList<>();
    }

    public void selecionar() {
        Empresa empresa = session.getObjectSession(ParametroSistema.EMPRESA_SESSION);
        if (empresa == null) {
            messages(new FacesMessage(getString("selecionar_empresa_erro")));
        } else {
            registros = hitRatioBufferCacheFacade.selecionarPorPeriodo(empresa.getId(), inicio, fim);
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

    public List<HitRatioBufferCache> getRegistros() {
        return registros;
    }

    public void setRegistros(List<HitRatioBufferCache> registros) {
        this.registros = registros;
    }
}
