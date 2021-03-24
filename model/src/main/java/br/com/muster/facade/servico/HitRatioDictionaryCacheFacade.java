/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.muster.facade.servico;

import br.com.muster.model.servico.HitRatioDictionaryCache;
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
public interface HitRatioDictionaryCacheFacade extends IFacade<HitRatioDictionaryCache, UUID> {

    void doPost(HitRatioDictionaryCache hitRatioDictionaryCache);
    
    void post(HitRatioDictionaryCache hitRatioDictionaryCache);

    List<HitRatioDictionaryCache> registrosPendentes();

    List<HitRatioDictionaryCache> selecionarPorPeriodo(UUID idEmpresa, Date inicio, Date fim);

    void removerEnviadosAntigos();
}
