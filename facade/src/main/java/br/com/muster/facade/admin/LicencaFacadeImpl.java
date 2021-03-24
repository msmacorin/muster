/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.muster.facade.admin;

import br.com.muster.dao.admin.LicencaDao;
import br.com.muster.facade.Facade;
import br.com.muster.model.admin.Licenca;
import br.com.muster.model.enums.ESituacao;
import in.macor.commons.security.PassGenerated;
import in.macor.core.dao.IDao;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.validation.ValidationException;
import org.joda.time.LocalDate;

/**
 *
 * @author macorin
 */
@Stateless
public class LicencaFacadeImpl extends Facade<Licenca, UUID> implements LicencaFacade {

    private final int TAMANHO_HASH_VALIDACAO = 50;

    @EJB
    private LicencaDao licencaDao;

    @Override
    protected IDao<Licenca, UUID> getDao() {
        return licencaDao;
    }

    @Override
    public boolean exclusaoVirtual() {
        return true;
    }

    @Override
    public void salvar(Licenca entity) throws EJBException {
        if (entity.getId() == null) {
            entity.setId(UUID.randomUUID());
            entity.setSituacao(ESituacao.Ativo);
            
            LocalDate prazo = getPrazo(entity);
            entity.setValidacao(PassGenerated.generated(prazo, TAMANHO_HASH_VALIDACAO));
        }

        super.salvar(entity);
    }

    @Override
    public void deletar(UUID id) throws EJBException {
        Licenca licenca = buscarPorId(id);
        licenca.setSituacao(ESituacao.Excluido);
        
        salvar(licenca);
    }
    
    @Override
    public boolean validarLicenca(Licenca licenca) throws ValidationException {
        // valida a situação do registro
        if (Arrays.asList(ESituacao.Excluido, ESituacao.Finalizado).contains(licenca.getSituacao())) {
            logger.info("Licenca nao esta ativa!");
            throw new ValidationException("licenca_nao_ativa");
        }

        LocalDate prazo = getPrazo(licenca);

        // valida se o prazo é menor que a data atual
        if (prazo.isBefore(new LocalDate())) {
            logger.info("O prazo de validade da licença expirou!");
            throw new ValidationException("licenca_expirou");
        }

        // valida se a hash de validaçao e igual de acordo com o prazo gerado.
        if (!(licenca.getValidacao().equals(PassGenerated.generated(prazo, TAMANHO_HASH_VALIDACAO)))) {
            logger.info("A hash de validação da licença não confere.");
            throw new ValidationException("licenca_invalida");
        }

        return true;
    }

    /**
     * calcula o prazo de validade da licenca.
     *
     * @param licenca
     * @return
     */
    private LocalDate getPrazo(Licenca licenca) {
        LocalDate prazo = new LocalDate(licenca.getInicio());
        prazo = prazo.plusMonths(licenca.getPrazo());

        return prazo;
    }

    @Override
    public List<Licenca> licencasAtivasDaEmpresa(UUID idEmpresa) {
        return licencaDao.licencasAtivasDaEmpresa(idEmpresa);
    }

}
