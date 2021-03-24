/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.muster.restful.admin;

import br.com.muster.facade.admin.UsuarioFacade;
import br.com.muster.model.admin.Usuario;
import br.com.muster.restful.AbstractResource;
import java.util.List;
import java.util.UUID;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author macorin
 */
@Stateless
@Path("/usuarios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UsuarioResource extends AbstractResource {

    @EJB
    private UsuarioFacade usuarioFacade;

    @GET
    @Path("/simples")
    public Response getAllUsuariosSimples() {
        try {
            List<Usuario> usuarios = usuarioFacade.getUsuariosSimples();
            return createResponse(usuarios);
        } catch (Exception ex) {
            return createErrorResponse();
        }
    }

    @GET
    public Response getUsuariosPorEmpresa(@QueryParam("idEmpresa") UUID idEmpresa) {
        try {
            List<Usuario> usuarios = usuarioFacade.getUsuariosDaEmpresa(idEmpresa);
            return createResponse(usuarios);
        } catch (Exception e) {
            return createErrorResponse();
        }
    }

    @GET
    @Path("/local")
    public Response getUsuarioLocal() {
        try {
            return createResponse(usuarioFacade.usuarioLocal());
        } catch (Exception e) {
            return createErrorResponse();
        }
    }
}
