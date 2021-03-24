/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.muster.facade.servico;

import br.com.muster.model.servico.Tablespace;
import in.macor.core.facade.IFacade;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.ejb.Local;

/**
 *
 * @author macorin
 */
@Local
public interface TablespaceFacade extends IFacade<Tablespace, UUID> {

    /**
     * faz o post do recurso na web.
     *
     * @param tablespace
     */
    void doPost(Tablespace tablespace);
    
    void post(Tablespace tablespace);

    List<Tablespace> registrosPendentes();
    
    List<Tablespace> selecionarPorPeriodo(UUID idEmpresa, Date inicio, Date fim);

    void removerEnviadosAntigos();
}
