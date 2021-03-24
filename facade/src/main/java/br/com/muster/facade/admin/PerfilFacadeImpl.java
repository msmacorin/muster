/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.muster.facade.admin;

import br.com.muster.dao.admin.PerfilDao;
import br.com.muster.facade.Facade;
import br.com.muster.model.admin.Perfil;
import br.com.muster.model.enums.ETipoPerfil;
import in.macor.core.dao.IDao;
import java.util.UUID;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Stateless;

/**
 *
 * @author macorin
 */
@Stateless
public class PerfilFacadeImpl extends Facade<Perfil, UUID> implements PerfilFacade {

    @EJB
    private PerfilDao perfilDao;

    @Override
    protected IDao<Perfil, UUID> getDao() {
        return perfilDao;
    }

    @Override
    public void salvar(Perfil entity) throws EJBException {
        if (entity.getId() == null) {
            entity.setId(UUID.randomUUID());
        }
        
        super.salvar(entity);
    }
    
    @Override
    public boolean exclusaoVirtual() {
        return false;
    }

    @Override
    public Perfil buscarPerfilPorTipo(ETipoPerfil tipoPerfil) {
        return perfilDao.buscarPerfilPorTipo(tipoPerfil);
    }
}
