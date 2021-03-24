/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.muster.restful.admin;

import br.com.muster.model.admin.Usuario;
import java.util.List;
import java.util.UUID;
import retrofit.RetrofitError;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 *
 * @author macorin
 */
public interface UsuarioService {
    
    @GET(value = "/usuarios/simples")
    List<Usuario> usuariosSimples() throws RetrofitError;
    
    @GET(value = "/usuarios")
    List<Usuario> usuarioDaEmpresa(@Query("idEmpresa") UUID idEmpresa) throws RetrofitError;
    
    @GET(value = "/usuarios/local")
    Usuario usuarioLocal() throws RetrofitError;
}
