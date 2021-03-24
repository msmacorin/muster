/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.muster.facade.servico;

import br.com.muster.dao.servico.FlashRecoveryAreaDao;
import br.com.muster.facade.Facade;
import br.com.muster.facade.admin.ConfiguracaoLocalFacade;
import br.com.muster.model.enums.EEnvio;
import br.com.muster.model.servico.FlashRecoveryArea;
import br.com.muster.restful.servico.FlashRecoveryAreaService;
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
public class FlashRecoveryAreaFacadeImpl extends Facade<FlashRecoveryArea, UUID> implements FlashRecoveryAreaFacade {

    @EJB
    private FlashRecoveryAreaDao flashRecoveryAreaDao;

    @EJB
    private ConfiguracaoLocalFacade configuracaoLocalFacade;

    @Override
    public void post(FlashRecoveryArea flashRecoveryArea) {
        flashRecoveryArea.setEnvio(EEnvio.Recebido);
        salvar(flashRecoveryArea);
    }

    @Override
    public void doPost(FlashRecoveryArea flashRecoveryArea) {
        try {
            flashRecoveryArea.setEnvio(null);
            flashRecoveryArea.setEmpresa(configuracaoLocalFacade.getEmpresaLocal());

            FlashRecoveryAreaService service = getService(
                    configuracaoLocalFacade.getUrlApi(),
                    configuracaoLocalFacade.getUsuarioLocal()
            ).create(FlashRecoveryAreaService.class);

            service.post(flashRecoveryArea);
            flashRecoveryArea.setEnvio(EEnvio.Enviado);
        } catch (RetrofitError e) {
            logger.error("Erro no POST de FlashRecoveryArea", e);
            flashRecoveryArea.setEnvio(EEnvio.Pendente);
        }

        salvar(flashRecoveryArea);
    }

    @Override
    public void salvar(FlashRecoveryArea entity) throws EJBException {
        if (entity.getId() == null) {
            entity.setId(UUID.randomUUID());
        }

        super.salvar(entity);
    }

    @Override
    protected IDao<FlashRecoveryArea, UUID> getDao() {
        return flashRecoveryAreaDao;
    }

    @Override
    public boolean exclusaoVirtual() {
        return false;
    }

    @Override
    public List<FlashRecoveryArea> registrosPendentes() {
        return flashRecoveryAreaDao.registrosPendentes();
    }

    @Override
    public void removerEnviadosAntigos() {
        List<FlashRecoveryArea> registros = flashRecoveryAreaDao.registrosEnviadosAntigos();
        for (FlashRecoveryArea f : registros) {
            flashRecoveryAreaDao.delete(f.getId());
        }
    }

    @Override
    public List<FlashRecoveryArea> selecionarPorPeriodo(UUID idEmpresa, Date inicio, Date fim) {
        return flashRecoveryAreaDao.selecionarPorPeriodo(idEmpresa, inicio, fim);
    }

}
