/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.muster.facade.servico;

import br.com.muster.dao.servico.ProcessoDao;
import br.com.muster.facade.Facade;
import br.com.muster.facade.admin.ConfiguracaoLocalFacade;
import br.com.muster.model.enums.EEnvio;
import br.com.muster.model.servico.Processo;
import br.com.muster.restful.servico.ProcessoService;
import in.macor.core.dao.IDao;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import retrofit.RetrofitError;

/**
 *
 * @author macorin
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class ProcessoFacadeImpl extends Facade<Processo, UUID> implements ProcessoFacade {

    @EJB
    private ProcessoDao processoDao;

    @EJB
    private ConfiguracaoLocalFacade configuracaoLocalFacade;

    @Override
    public void post(Processo processo) {
        processo.setEnvio(EEnvio.Recebido);
        salvar(processo);
    }

    @Override
    public void doPost(Processo processo) {
        try {
            processo.setEnvio(null);
            processo.setEmpresa(configuracaoLocalFacade.getEmpresaLocal());

            ProcessoService service = getService(
                    configuracaoLocalFacade.getUrlApi(),
                    configuracaoLocalFacade.getUsuarioLocal()
            ).create(ProcessoService.class);
            
            service.post(processo);
            
            processo.setEnvio(EEnvio.Enviado);
        } catch (RetrofitError e) {
            logger.error("Erro no POST de processo", e);
            processo.setEnvio(EEnvio.Pendente);
        }

        salvar(processo);
    }

    @Override
    public void salvar(Processo entity) throws EJBException {
        if (entity.getId() == null) {
            entity.setId(UUID.randomUUID());
        }

        super.salvar(entity);
    }

    @Override
    protected IDao<Processo, UUID> getDao() {
        return processoDao;
    }

    @Override
    public boolean exclusaoVirtual() {
        return false;
    }

    @Override
    public List<Processo> registrosPendentes() {
        return processoDao.registrosPendentes();
    }

    @Override
    public void removerEnviadosAntigos() {
        List<Processo> registros = processoDao.registrosEnviadosAntigos();
        for (Processo p : registros) {
            processoDao.delete(p.getId());
        }
    }

    @Override
    public List<Processo> selecionarPorPeriodo(UUID idEmpresa, Date inicio, Date fim) {
        return processoDao.selecionarPorPeriodo(idEmpresa, inicio, fim);
    }

}
