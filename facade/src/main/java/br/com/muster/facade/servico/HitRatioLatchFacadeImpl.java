/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.muster.facade.servico;

import br.com.muster.dao.servico.HitRatioLatchDao;
import br.com.muster.facade.Facade;
import br.com.muster.facade.admin.ConfiguracaoLocalFacade;
import br.com.muster.model.enums.EEnvio;
import br.com.muster.model.servico.HitRatioLatch;
import br.com.muster.restful.servico.HitRatioLatchService;
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
public class HitRatioLatchFacadeImpl extends Facade<HitRatioLatch, UUID> implements HitRatioLatchFacade {

    @EJB
    private HitRatioLatchDao hitRatioLatchDao;

    @EJB
    private ConfiguracaoLocalFacade configuracaoLocalFacade;

    @Override
    public void post(HitRatioLatch hitRatioLatch) {
        hitRatioLatch.setEnvio(EEnvio.Recebido);
        salvar(hitRatioLatch);
    }

    @Override
    public void doPost(HitRatioLatch hitRatioLatch) {
        try {
            hitRatioLatch.setEnvio(null);
            hitRatioLatch.setEmpresa(configuracaoLocalFacade.getEmpresaLocal());

            HitRatioLatchService service = getService(
                    configuracaoLocalFacade.getUrlApi(),
                    configuracaoLocalFacade.getUsuarioLocal()
            ).create(HitRatioLatchService.class);

            service.post(hitRatioLatch);
            
            hitRatioLatch.setEnvio(EEnvio.Enviado);
        } catch (RetrofitError e) {
            logger.error("Erro no POST de HitRatioLatch", e);
            hitRatioLatch.setEnvio(EEnvio.Pendente);
        }

        salvar(hitRatioLatch);
    }

    @Override
    public void salvar(HitRatioLatch entity) throws EJBException {
        if (entity.getId() == null) {
            entity.setId(UUID.randomUUID());
        }

        super.salvar(entity);
    }

    @Override
    protected IDao<HitRatioLatch, UUID> getDao() {
        return hitRatioLatchDao;
    }

    @Override
    public boolean exclusaoVirtual() {
        return false;
    }

    @Override
    public List<HitRatioLatch> registrosPendentes() {
        return hitRatioLatchDao.registrosPendentes();
    }

    @Override
    public void removerEnviadosAntigos() {
        List<HitRatioLatch> registros = hitRatioLatchDao.registrosEnviadosAntigos();
        for (HitRatioLatch l : registros) {
            hitRatioLatchDao.delete(l.getId());
        }
    }

    @Override
    public List<HitRatioLatch> selecionarPorPeriodo(UUID idEmpresa, Date inicio, Date fim) {
        return hitRatioLatchDao.selecionarPorPeriodo(idEmpresa, inicio, fim);
    }

}
