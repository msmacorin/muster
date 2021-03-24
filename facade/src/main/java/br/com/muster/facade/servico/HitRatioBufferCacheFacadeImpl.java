/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.muster.facade.servico;

import br.com.muster.dao.servico.HitRatioBufferCacheDao;
import br.com.muster.facade.Facade;
import br.com.muster.facade.admin.ConfiguracaoLocalFacade;
import br.com.muster.model.enums.EEnvio;
import br.com.muster.model.servico.HitRatioBufferCache;
import br.com.muster.restful.servico.HitRatioBufferCacheService;
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
public class HitRatioBufferCacheFacadeImpl extends Facade<HitRatioBufferCache, UUID> implements HitRatioBufferCacheFacade {

    @EJB
    private HitRatioBufferCacheDao hitRatioBufferCacheDao;

    @EJB
    private ConfiguracaoLocalFacade configuracaoLocalFacade;

    @Override
    public void post(HitRatioBufferCache hitRatioBufferCache) {
        hitRatioBufferCache.setEnvio(EEnvio.Recebido);
        salvar(hitRatioBufferCache);
    }

    @Override
    public void doPost(HitRatioBufferCache hitRatioBufferCache) {
        try {
            hitRatioBufferCache.setEnvio(null);
            hitRatioBufferCache.setEmpresa(configuracaoLocalFacade.getEmpresaLocal());

            HitRatioBufferCacheService service = getService(
                    configuracaoLocalFacade.getUrlApi(),
                    configuracaoLocalFacade.getUsuarioLocal()
            ).create(HitRatioBufferCacheService.class);

            service.post(hitRatioBufferCache);
            hitRatioBufferCache.setEnvio(EEnvio.Enviado);
        } catch (RetrofitError e) {
            logger.error("Erro no POST de HitRatioBufferCache", e);
            hitRatioBufferCache.setEnvio(EEnvio.Pendente);
        }

        salvar(hitRatioBufferCache);
    }

    @Override
    public void salvar(HitRatioBufferCache entity) throws EJBException {
        if (entity.getId() == null) {
            entity.setId(UUID.randomUUID());
        }

        super.salvar(entity);
    }

    @Override
    protected IDao<HitRatioBufferCache, UUID> getDao() {
        return hitRatioBufferCacheDao;
    }

    @Override
    public boolean exclusaoVirtual() {
        return false;
    }

    @Override
    public List<HitRatioBufferCache> registrosPendentes() {
        return hitRatioBufferCacheDao.registrosPendentes();
    }

    @Override
    public void removerEnviadosAntigos() {
        List<HitRatioBufferCache> registros = hitRatioBufferCacheDao.registrosEnviadosAntigos();
        for (HitRatioBufferCache h : registros) {
            hitRatioBufferCacheDao.delete(h.getId());
        }
    }

    @Override
    public List<HitRatioBufferCache> selecionarPorPeriodo(UUID idEmpresa, Date inicio, Date fim) {
        return hitRatioBufferCacheDao.selecionarPorPeriodo(idEmpresa, inicio, fim);
    }

}
