/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.muster.dao.servico;

import br.com.muster.model.servico.Lock;
import in.macor.core.dao.IDao;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.ejb.Local;

/**
 *
 * @author macorin
 */
@Local
public interface LockDao extends IDao<Lock, UUID>{
    
    List<Lock> registrosPendentes();

    List<Lock> registrosEnviadosAntigos();
    
    List<Lock> selecionarPorPeriodo(UUID idEmpresa, Date inicio, Date fim);
}
