/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.muster.facade.servico;

import br.com.muster.dao.servico.HitRatioLibraryCacheDao;
import br.com.muster.facade.Facade;
import br.com.muster.facade.admin.ConfiguracaoLocalFacade;
import br.com.muster.model.enums.EEnvio;
import br.com.muster.model.servico.HitRatioLibraryCache;
import br.com.muster.restful.servico.HitRatioLibraryCacheService;
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
public class HitRatioLibraryCacheFacadeImpl extends Facade<HitRatioLibraryCache, UUID> implements HitRatioLibraryCacheFacade {

    @EJB
    private HitRatioLibraryCacheDao hitRatioLibraryCacheDao;

    @EJB
    private ConfiguracaoLocalFacade configuracaoLocalFacade;

    @Override
    public void post(HitRatioLibraryCache hitRatioLibraryCache) {
        hitRatioLibraryCache.setEnvio(EEnvio.Recebido);
        salvar(hitRatioLibraryCache);
    }

    @Override
    public void doPost(HitRatioLibraryCache hitRatioLibraryCache) {
        try {
            hitRatioLibraryCache.setEnvio(null);
            hitRatioLibraryCache.setEmpresa(configuracaoLocalFacade.getEmpresaLocal());

            HitRatioLibraryCacheService service = getService(
                    configuracaoLocalFacade.getUrlApi(),
                    configuracaoLocalFacade.getUsuarioLocal()
            ).create(HitRatioLibraryCacheService.class);

            service.post(hitRatioLibraryCache);
            
            hitRatioLibraryCache.setEnvio(EEnvio.Enviado);
        } catch (RetrofitError e) {
            logger.error("Erro no POST de HitRatioLibraryCache", e);
            hitRatioLibraryCache.setEnvio(EEnvio.Pendente);
        }

        salvar(hitRatioLibraryCache);
    }

    @Override
    public void salvar(HitRatioLibraryCache entity) throws EJBException {
        if (entity.getId() == null) {
            entity.setId(UUID.randomUUID());
        }

        super.salvar(entity);
    }

    @Override
    protected IDao<HitRatioLibraryCache, UUID> getDao() {
        return hitRatioLibraryCacheDao;
    }

    @Override
    public boolean exclusaoVirtual() {
        return false;
    }

    @Override
    public List<HitRatioLibraryCache> registrosPendentes() {
        return hitRatioLibraryCacheDao.registrosPendentes();
    }

    @Override
    public void removerEnviadosAntigos() {
        List<HitRatioLibraryCache> registros = hitRatioLibraryCacheDao.registrosEnviadosAntigos();
        for (HitRatioLibraryCache h : registros) {
            hitRatioLibraryCacheDao.delete(h.getId());
        }
    }

    @Override
    public List<HitRatioLibraryCache> selecionarPorPeriodo(UUID idEmpresa, Date inicio, Date fim) {
        return hitRatioLibraryCacheDao.selecionarPorPeriodo(idEmpresa, inicio, fim);
    }

}
