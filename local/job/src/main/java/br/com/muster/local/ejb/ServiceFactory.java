/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.muster.local.ejb;

import br.com.muster.local.service.FlashRecoveryAreaService;
import br.com.muster.local.service.HitRatioBufferCacheService;
import br.com.muster.local.service.HitRatioDictionaryCacheService;
import br.com.muster.local.service.HitRatioLatchService;
import br.com.muster.local.service.HitRatioLibraryCacheService;
import br.com.muster.local.service.LockService;
import br.com.muster.local.service.ProcessoService;
import br.com.muster.local.service.Service;
import br.com.muster.local.service.TablespaceService;
import java.util.LinkedList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author macorin
 */
@Stateless
public class ServiceFactory {

    @EJB
    private ProcessoService processoService;

    @EJB
    private FlashRecoveryAreaService flashRecoveryAreaService;

    @EJB
    private HitRatioBufferCacheService hitRatioBufferCacheService;

    @EJB
    private HitRatioDictionaryCacheService hitRatioDictionaryCacheService;

    @EJB
    private HitRatioLatchService hitRatioLatchService;

    @EJB
    private HitRatioLibraryCacheService hitRatioLibraryCacheService;

    @EJB
    private LockService lockService;

    @EJB
    private TablespaceService tablespaceService;

    public List<Service> getServices() {
        List<Service> services = new LinkedList<>();

        services.add(processoService);
        services.add(flashRecoveryAreaService);
        services.add(hitRatioBufferCacheService);
        services.add(hitRatioDictionaryCacheService);
        services.add(hitRatioLatchService);
        services.add(hitRatioLibraryCacheService);
        services.add(lockService);
        services.add(tablespaceService);

        return services;
    }
}
