/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.muster.dao.admin;

import br.com.muster.dao.Dao;
import br.com.muster.model.admin.Licenca;
import br.com.muster.model.enums.ESituacao;
import java.util.List;
import java.util.UUID;
import javax.ejb.Stateless;

/**
 *
 * @author macorin
 */
@Stateless
public class LicencaDaoImpl extends Dao<Licenca, UUID> implements LicencaDao{
    private static final long serialVersionUID = -8750706279623991679L;

    private final String SELECT_LICENSE_ACTIVES = "SELECT l "
            + "FROM Licenca l "
            + "WHERE l.situacao = :situacao "
            + "AND l.empresa.id = :idEmpresa";
    
    @Override
    public List<Licenca> licencasAtivasDaEmpresa(UUID idEmpresa) {
        try {
            params.put("situacao", ESituacao.Ativo);
            params.put("idEmpresa", idEmpresa);
            return select(SELECT_LICENSE_ACTIVES);
        } finally {
            params.clear();
        }
    }
    
}
