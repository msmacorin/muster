/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.muster.restful;

import java.util.List;
import javax.ws.rs.core.Response;

/**
 *
 * @author macorin
 */
public abstract class AbstractResource {
    
    public Response createResponse(Object obj) {
        if ((obj == null)
                || ((obj instanceof List) && (((List) obj).isEmpty()))){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        
        return Response.ok(obj).build();
    }
    
    public Response createErrorResponse() {
        return Response.serverError().build();
    }
}
