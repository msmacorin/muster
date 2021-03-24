/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.muster.local.admin;

import br.com.muster.facade.admin.ConfiguracaoLocalFacade;
import br.com.muster.local.AbstractBean;
import br.com.muster.model.admin.ConfiguracaoLocal;
import br.com.muster.model.enums.ESimNao;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author macorin
 */
@ManagedBean
@RequestScoped
public class ServicosBean extends AbstractBean {

    @EJB
    private ConfiguracaoLocalFacade configuracaoLocalFacade;

    private ConfiguracaoLocal configuracaoLocal;

    @PostConstruct
    public void init() {
        configuracaoLocal = configuracaoLocalFacade.buscarPorId(1L);
    }

    public boolean servicoRodando() {
        if (configuracaoLocal == null) {
            return false;
        }

        return ESimNao.Sim.equals(configuracaoLocal.getExecutarServicos());
    }

    public void alterarStatus() {
        try {
            if (configuracaoLocal != null) {
                configuracaoLocal.setExecutarServicos(ESimNao.Sim.equals(configuracaoLocal.getExecutarServicos())
                        ? ESimNao.Nao
                        : ESimNao.Sim);

                configuracaoLocalFacade.salvar(configuracaoLocal);
                messages(new FacesMessage(getString("servico_status_sucesso")));
            } else {
                messages(new FacesMessage(getString("servico_status_erro_configuracao")));
            }
        } catch (EJBException e) {
            logger.error("Erro em tentar alterar status do serviço", e);
            messages(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    getString("servico_status_erro"),
                    e.getMessage()));
        }
    }

}
