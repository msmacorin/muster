/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.muster.dao.admin;

import br.com.muster.dao.Dao;
import br.com.muster.model.admin.Perfil;
import br.com.muster.model.enums.ETipoPerfil;
import java.util.UUID;
import javax.ejb.Stateless;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author macorin
 */
@Stateless
public class PerfilDaoImpl extends Dao<Perfil, UUID> implements PerfilDao {
    private static final long serialVersionUID = 7622740941359200073L;

    @Override
    public Perfil buscarPerfilPorTipo(ETipoPerfil tipoPerfil) {
        return findOneResult(Restrictions.eq(Perfil.PerfilColumns.tipoPerfil, tipoPerfil));
    }

}
