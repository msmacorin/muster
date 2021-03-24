/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.muster.dao.servico;

import br.com.muster.model.servico.Processo;
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
public interface ProcessoDao extends IDao<Processo, UUID> {

    List<Processo> registrosPendentes();

    List<Processo> registrosEnviadosAntigos();
    
    List<Processo> selecionarPorPeriodo(UUID idEmpresa, Date inicio, Date fim);
}
