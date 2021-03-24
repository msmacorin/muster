/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.muster.restful.servico;

import br.com.muster.model.servico.HitRatioDictionaryCache;
import retrofit.http.Body;
import retrofit.http.POST;

/**
 *
 * @author macorin
 */
public interface HitRatioDictionaryCacheService {

    @POST("/hit_ratio_dictionary_cache")
    HitRatioDictionaryCache post(@Body HitRatioDictionaryCache hitRatioDictionaryCache);
}
