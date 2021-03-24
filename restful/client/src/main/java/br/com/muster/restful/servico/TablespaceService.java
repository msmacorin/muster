/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.muster.restful.servico;

import br.com.muster.model.servico.Tablespace;
import retrofit.http.Body;
import retrofit.http.POST;

/**
 *
 * @author macorin
 */
public interface TablespaceService {
        
    @POST("/tablespace")
    Tablespace post(@Body Tablespace tablespace);
}
