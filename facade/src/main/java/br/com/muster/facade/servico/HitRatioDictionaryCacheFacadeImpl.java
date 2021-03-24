/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.muster.facade.servico;

import br.com.muster.dao.servico.HitRatioDictionaryCacheDao;
import br.com.muster.facade.Facade;
import br.com.muster.facade.admin.ConfiguracaoLocalFacade;
import br.com.muster.model.enums.EEnvio;
import br.com.muster.model.servico.HitRatioDictionaryCache;
import br.com.muster.restful.servico.HitRatioDictionaryCacheService;
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
public class HitRatioDictionaryCacheFacadeImpl extends Facade<HitRatioDictionaryCache, UUID> implements HitRatioDictionaryCacheFacade {

    @EJB
    private HitRatioDictionaryCacheDao hitRatioDictionaryCacheDao;

    @EJB
    private ConfiguracaoLocalFacade configuracaoLocalFacade;

    @Override
    public void post(HitRatioDictionaryCache hitRatioDictionaryCache) {
        hitRatioDictionaryCache.setEnvio(EEnvio.Recebido);
        salvar(hitRatioDictionaryCache);
    }

    @Override
    public void doPost(HitRatioDictionaryCache hitRatioDictionaryCache) {
        try {
            hitRatioDictionaryCache.setEnvio(null);
            hitRatioDictionaryCache.setEmpresa(configuracaoLocalFacade.getEmpresaLocal());

            HitRatioDictionaryCacheService service = getService(
                    configuracaoLocalFacade.getUrlApi(),
                    configuracaoLocalFacade.getUsuarioLocal()
            ).create(HitRatioDictionaryCacheService.class);

            service.post(hitRatioDictionaryCache);
            
            hitRatioDictionaryCache.setEnvio(EEnvio.Enviado);
        } catch (RetrofitError e) {
            logger.error("Erro no POST de HitRatioDictionaryCache", e);
            hitRatioDictionaryCache.setEnvio(EEnvio.Pendente);
        }

        salvar(hitRatioDictionaryCache);
    }

    @Override
    public void salvar(HitRatioDictionaryCache entity) throws EJBException {
        if (entity.getId() == null) {
            entity.setId(UUID.randomUUID());
        }

        super.salvar(entity);
    }

    @Override
    protected IDao<HitRatioDictionaryCache, UUID> getDao() {
        return hitRatioDictionaryCacheDao;
    }

    @Override
    public boolean exclusaoVirtual() {
        return false;
    }

    @Override
    public List<HitRatioDictionaryCache> registrosPendentes() {
        return hitRatioDictionaryCacheDao.registrosPendentes();
    }

    @Override
    public void removerEnviadosAntigos() {
        List<HitRatioDictionaryCache> enviados = hitRatioDictionaryCacheDao.registrosEnviadosAntigos();
        for (HitRatioDictionaryCache h : enviados) {
            deletar(h.getId());
        }
    }

    @Override
    public List<HitRatioDictionaryCache> selecionarPorPeriodo(UUID idEmpresa, Date inicio, Date fim) {
        return hitRatioDictionaryCacheDao.selecionarPorPeriodo(idEmpresa, inicio, fim);
    }

}
