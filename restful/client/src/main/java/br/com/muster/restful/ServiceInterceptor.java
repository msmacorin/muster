/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.muster.restful;

import br.com.muster.model.admin.ParametroSistema;
import in.macor.commons.util.StringUtil;
import retrofit.RequestInterceptor;

/**
 *
 * @author macorin
 */
public class ServiceInterceptor implements RequestInterceptor{

    String base64;
    
    public ServiceInterceptor(String user, String pass) {
        base64 = StringUtil.encodeBase64(String.format("%s:%s", user, pass));
    }

    @Override
    public void intercept(RequestFacade requestFacade) {
        requestFacade.addHeader(ParametroSistema.AUTHORIZATION_PROPERTY, 
                ParametroSistema.AUTHENTICATION_SCHEME + " " + base64);
    }
    
}
