/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.muster.facade.admin;

import br.com.muster.facade.Facade;
import br.com.muster.dao.admin.EmpresaDao;
import br.com.muster.model.admin.Empresa;
import br.com.muster.model.admin.Licenca;
import br.com.muster.model.admin.Usuario;
import br.com.muster.model.enums.ESituacao;
import br.com.muster.restful.admin.EmpresaService;
import in.macor.core.dao.IDao;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import retrofit.RetrofitError;

/**
 *
 * @author macorin
 */
@Stateless
public class EmpresaFacadeImpl extends Facade<Empresa, UUID> implements EmpresaFacade {

    @EJB
    private EmpresaDao empresaDao;

    @EJB
    private LicencaFacade licencaFacade;

    @EJB
    private ConfiguracaoLocalFacade configuracaoLocalFacade;

    @Override
    protected IDao<Empresa, UUID> getDao() {
        return empresaDao;
    }

    @Override
    public boolean exclusaoVirtual() {
        return true;
    }

    @Override
    public void salvar(Empresa entity) throws EJBException {
        if (entity.getId() == null) {
            entity.setId(UUID.randomUUID());
            entity.setSituacao(ESituacao.Ativo);
        }
        
        super.salvar(entity);
    }

    @Override
    public List<Empresa> empresasLicenciadas() {
        List<Empresa> empresas = empresaDao.findAll();
        List<Empresa> empresasLicenciadas = new ArrayList<>();

        for (Empresa empresa : empresas) {
            for (Licenca licenca : licencaFacade.licencasAtivasDaEmpresa(empresa.getId())) {
                if (licencaFacade.validarLicenca(licenca)) {
                    empresasLicenciadas.add(empresa);
                }
            }
        }

        return empresasLicenciadas;
    }

    @Override
    public List<Empresa> empresasLicenciadasRemoto(Usuario usuarioLogado) {
        try {
            EmpresaService service = getService(configuracaoLocalFacade.getUrlApi(), usuarioLogado).
                    create(EmpresaService.class);

            return service.empresasLicenciadas();
        } catch (RetrofitError e) {
            logger.info("EmpresaFacadeImpl: empresasLicenciadasRemoto", e);
        }
        
        return new ArrayList<>();
    }

}
