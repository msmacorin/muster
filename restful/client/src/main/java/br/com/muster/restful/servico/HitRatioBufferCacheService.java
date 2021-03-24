/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.muster.restful.servico;

import br.com.muster.model.servico.HitRatioBufferCache;
import retrofit.http.Body;
import retrofit.http.POST;

/**
 *
 * @author macorin
 */
public interface HitRatioBufferCacheService {

    @POST("/hit_ratio_buffer_cache")
    HitRatioBufferCache post(@Body HitRatioBufferCache hitRatioBufferCache);
}
