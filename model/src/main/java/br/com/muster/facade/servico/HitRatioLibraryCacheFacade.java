/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.muster.facade.servico;

import br.com.muster.model.servico.HitRatioLibraryCache;
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
public interface HitRatioLibraryCacheFacade extends IFacade<HitRatioLibraryCache, UUID> {

    void doPost(HitRatioLibraryCache hitRatioLibraryCache);
    
    void post(HitRatioLibraryCache hitRatioLibraryCache);

    List<HitRatioLibraryCache> registrosPendentes();

    List<HitRatioLibraryCache> selecionarPorPeriodo(UUID idEmpresa, Date inicio, Date fim);

    void removerEnviadosAntigos();
}
