/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.muster.facade.admin;

import br.com.muster.dao.admin.ConfiguracaoLocalDao;
import br.com.muster.facade.Facade;
import br.com.muster.model.admin.ConfiguracaoLocal;
import br.com.muster.model.admin.Empresa;
import br.com.muster.model.admin.ParametroSistema;
import br.com.muster.model.admin.Usuario;
import br.com.muster.model.enums.ESimNao;
import in.macor.core.dao.IDao;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author macorin
 */
@Stateless
public class ConfiguracaoLocalFacadeImpl extends Facade<ConfiguracaoLocal, Long> implements ConfiguracaoLocalFacade {

    @EJB
    private ConfiguracaoLocalDao configuracaoLocalDao;

    private ConfiguracaoLocal configuracaoLocal;

    @PostConstruct
    public void init() {
        configuracaoLocal = configuracaoLocalDao.findById(1l);
    }

    @Override
    protected IDao<ConfiguracaoLocal, Long> getDao() {
        return configuracaoLocalDao;
    }

    @Override
    public boolean exclusaoVirtual() {
        return false;
    }

    @Override
    public Usuario getUsuarioLocal() {
        return configuracaoLocal == null
                ? null
                : configuracaoLocal.getUsuario();
    }

    @Override
    public Empresa getEmpresaLocal() {
        return configuracaoLocal == null
                ? null
                : configuracaoLocal.getEmpresa();
    }

    @Override
    public String getUrlApi() {
        return configuracaoLocal == null
                ? ParametroSistema.URL_API
                : configuracaoLocal.getUrlApi();
    }

    @Override
    public String getEmailAviso() {
        return configuracaoLocal == null
                ? null
                : configuracaoLocal.getEmail();
    }

    @Override
    public boolean executarServicos() {
        return configuracaoLocal == null
                ? false
                : ESimNao.Sim.equals(configuracaoLocal.getExecutarServicos());
    }
}
