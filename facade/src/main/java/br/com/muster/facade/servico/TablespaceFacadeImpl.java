/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.muster.facade.servico;

import br.com.muster.dao.servico.TablespaceDao;
import br.com.muster.facade.Facade;
import br.com.muster.facade.admin.ConfiguracaoLocalFacade;
import br.com.muster.model.enums.EEnvio;
import br.com.muster.model.servico.Tablespace;
import br.com.muster.restful.servico.TablespaceService;
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
public class TablespaceFacadeImpl extends Facade<Tablespace, UUID> implements TablespaceFacade {

    @EJB
    private TablespaceDao tablespaceDao;

    @EJB
    private ConfiguracaoLocalFacade configuracaoLocalFacade;

    @Override
    public void post(Tablespace tablespace) {
        tablespace.setEnvio(EEnvio.Recebido);
        salvar(tablespace);
    }

    @Override
    public void doPost(Tablespace tablespace) {
        try {
            tablespace.setEnvio(null);
            tablespace.setEmpresa(configuracaoLocalFacade.getEmpresaLocal());

            TablespaceService service = getService(
                    configuracaoLocalFacade.getUrlApi(),
                    configuracaoLocalFacade.getUsuarioLocal()
            ).create(TablespaceService.class);

            service.post(tablespace);
            tablespace.setEnvio(EEnvio.Enviado);
        } catch (RetrofitError e) {
            logger.error("Erro no POST de tablespace", e);
            tablespace.setEnvio(EEnvio.Pendente);
        }
        salvar(tablespace);
    }

    @Override
    public void salvar(Tablespace entity) throws EJBException {
        if (entity.getId() == null) {
            entity.setId(UUID.randomUUID());
        }

        super.salvar(entity);
    }

    @Override
    protected IDao<Tablespace, UUID> getDao() {
        return tablespaceDao;
    }

    @Override
    public boolean exclusaoVirtual() {
        return false;
    }

    @Override
    public List<Tablespace> registrosPendentes() {
        return tablespaceDao.registrosPendentes();
    }

    @Override
    public void removerEnviadosAntigos() {
        List<Tablespace> registros = tablespaceDao.registrosEnviadosAntigos();
        for (Tablespace t : registros) {
            tablespaceDao.delete(t.getId());
        }
    }

    @Override
    public List<Tablespace> selecionarPorPeriodo(UUID idEmpresa, Date inicio, Date fim) {
        return tablespaceDao.selecionarPorPeriodo(idEmpresa, inicio, fim);
    }

}
