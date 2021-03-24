/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.muster.facade.servico;

import br.com.muster.model.servico.HitRatioLatch;
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
public interface HitRatioLatchFacade extends IFacade<HitRatioLatch, UUID> {

    void doPost(HitRatioLatch hitRatioLatch);
    
    void post(HitRatioLatch hitRatioLatch);

    List<HitRatioLatch> registrosPendentes();
    
    List<HitRatioLatch> selecionarPorPeriodo(UUID idEmpresa, Date inicio, Date fim);

    void removerEnviadosAntigos();
}
