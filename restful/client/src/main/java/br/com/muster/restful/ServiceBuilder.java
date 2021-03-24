/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.muster.restful;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import in.macor.commons.util.StringUtil;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 *
 * @author macorin
 */
public class ServiceBuilder {

    private final RestAdapter restAdapter;

    public ServiceBuilder(String url, String user, String pass) {
        Gson gson = new GsonBuilder()
                .setDateFormat("dd/MM/yyyy HH:mm:ss.SSSS")
                .create();

        if (!StringUtil.empty(user)) {
            restAdapter = new RestAdapter.Builder()
                    .setEndpoint(url)
                    .setRequestInterceptor(new ServiceInterceptor(user, pass))
                    .setConverter(new GsonConverter(gson))
                    .build();
        } else {
            restAdapter = new RestAdapter.Builder()
                    .setEndpoint(url)
                    .setConverter(new GsonConverter(gson))
                    .build();
        }
    }
    
    public <T extends Object> T create(Class<T> service) {
        return restAdapter.create(service);
    }
}
