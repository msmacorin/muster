/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.muster.facade.servico;

import br.com.muster.dao.servico.LockDao;
import br.com.muster.facade.Facade;
import br.com.muster.facade.admin.ConfiguracaoLocalFacade;
import br.com.muster.model.enums.EEnvio;
import br.com.muster.model.servico.Lock;
import br.com.muster.restful.servico.LockService;
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
public class LockFacadeImpl extends Facade<Lock, UUID> implements LockFacade {

    @EJB
    private LockDao lockDao;

    @EJB
    private ConfiguracaoLocalFacade configuracaoLocalFacade;

    @Override
    public void post(Lock lock) {
        lock.setEnvio(EEnvio.Recebido);
        salvar(lock);
    }

    @Override
    public void doPost(Lock lock) {
        try {
            lock.setEnvio(null);
            lock.setEmpresa(configuracaoLocalFacade.getEmpresaLocal());

            LockService service = getService(
                    configuracaoLocalFacade.getUrlApi(),
                    configuracaoLocalFacade.getUsuarioLocal()
            ).create(LockService.class);

            service.post(lock);
            
            lock.setEnvio(EEnvio.Enviado);
        } catch (RetrofitError e) {
            logger.error("Erro no POST de Lock", e);
            lock.setEnvio(EEnvio.Pendente);
        }

        salvar(lock);
    }

    @Override
    public void salvar(Lock entity) throws EJBException {
        if (entity.getId() == null) {
            entity.setId(UUID.randomUUID());
        }

        super.salvar(entity);
    }

    @Override
    protected IDao<Lock, UUID> getDao() {
        return lockDao;
    }

    @Override
    public boolean exclusaoVirtual() {
        return false;
    }

    @Override
    public List<Lock> registrosPendentes() {
        return lockDao.registrosPendentes();
    }

    @Override
    public void removerEnviadosAntigos() {
        List<Lock> registros = lockDao.registrosEnviadosAntigos();
        for (Lock l : registros) {
            lockDao.delete(l.getId());
        }
    }

    @Override
    public List<Lock> selecionarPorPeriodo(UUID idEmpresa, Date inicio, Date fim) {
        return lockDao.selecionarPorPeriodo(idEmpresa, inicio, fim);
    }

}
