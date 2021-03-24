/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.netbeans.rest.application.config;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author macorin
 */
@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        // following code can be used to customize Jersey 1.x JSON provider:
        try {
            Class jacksonProvider = Class.forName("org.codehaus.jackson.jaxrs.JacksonJsonProvider");
            resources.add(jacksonProvider);
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(getClass().getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(br.com.muster.restful.admin.EmpresaResource.class);
        resources.add(br.com.muster.restful.admin.UsuarioResource.class);
        resources.add(br.com.muster.restful.provider.JacksonConfig.class);
        resources.add(br.com.muster.restful.security.SecurityInterceptor.class);
        resources.add(br.com.muster.restful.servico.HitRatioBufferCacheResource.class);
        resources.add(br.com.muster.restful.servico.HitRatioDictionaryCacheResource.class);
        resources.add(br.com.muster.restful.servico.HitRatioLatchResource.class);
        resources.add(br.com.muster.restful.servico.HitRatioLibraryCacheResource.class);
        resources.add(br.com.muster.restful.servico.LockResource.class);
        resources.add(br.com.muster.restful.servico.ProcessoResource.class);
        resources.add(br.com.muster.restful.servico.TablespaceResource.class);
    }
    
}
