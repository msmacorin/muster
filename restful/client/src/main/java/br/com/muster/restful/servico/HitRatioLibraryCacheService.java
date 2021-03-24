/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.muster.restful.servico;

import br.com.muster.model.servico.HitRatioLibraryCache;
import retrofit.http.Body;
import retrofit.http.POST;

/**
 *
 * @author macorin
 */
public interface HitRatioLibraryCacheService {

    @POST("/hit_ratio_library_cache")
    HitRatioLibraryCache post(@Body HitRatioLibraryCache hitRatioLibraryCache);
}
