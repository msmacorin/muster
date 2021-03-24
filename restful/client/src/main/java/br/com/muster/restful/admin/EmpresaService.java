/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.muster.restful.admin;

import br.com.muster.model.admin.Empresa;
import java.util.List;
import retrofit.RetrofitError;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 *
 * @author macorin
 */
public interface EmpresaService {

    @GET("/empresas/{id}")
    Empresa get(@Path("id") Long id) throws RetrofitError;
          
    @GET("/empresas/licenciadas")
    List<Empresa> empresasLicenciadas()throws RetrofitError;
}
