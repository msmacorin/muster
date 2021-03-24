/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.muster.restful.servico;

import br.com.muster.model.servico.FlashRecoveryArea;
import retrofit.RetrofitError;
import retrofit.http.Body;
import retrofit.http.POST;

/**
 *
 * @author macorin
 */
public interface FlashRecoveryAreaService {

    @POST("/flash_recovery_area")
    FlashRecoveryArea post(@Body FlashRecoveryArea flashRecoveryArea) throws RetrofitError;
}
