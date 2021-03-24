/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.muster.restful.servico;

import br.com.muster.facade.servico.HitRatioLibraryCacheFacade;
import br.com.muster.model.servico.HitRatioLibraryCache;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author macorin
 */
@Stateless
@Path("/hit_ratio_library_cache")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class HitRatioLibraryCacheResource {
    
    @EJB
    private HitRatioLibraryCacheFacade hitRatioLibraryCacheFacade;
    
    @POST
    public Response post(HitRatioLibraryCache hitRatioLibraryCache) {
        hitRatioLibraryCacheFacade.post(hitRatioLibraryCache);
        return Response.status(Response.Status.CREATED).build();
    }
}
