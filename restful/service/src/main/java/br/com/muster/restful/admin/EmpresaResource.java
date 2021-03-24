/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.muster.restful.admin;

import br.com.muster.facade.admin.EmpresaFacade;
import br.com.muster.restful.AbstractResource;
import java.util.UUID;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author macorin
 */
@Stateless
@Path("/empresas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EmpresaResource extends AbstractResource {

    @EJB
    private EmpresaFacade empresaFacade;

    @GET
    public Response getAll() {
        try {
            return createResponse(empresaFacade.selecionarTodos());
        } catch (RuntimeException e) {
            return createErrorResponse();
        }
    }

    @GET
    @Path("{id}")
    public Response get(@PathParam("id") UUID id) {
        try {
            return createResponse(empresaFacade.buscarPorId(id));
        } catch (RuntimeException e) {
            return createErrorResponse();
        }
    }

    @GET
    @Path("/licenciadas")
    public Response empresasLicenciadas() {
        try {
            return createResponse(empresaFacade.empresasLicenciadas());
        } catch (RuntimeException e) {
            return createErrorResponse();
        }
    }

}
