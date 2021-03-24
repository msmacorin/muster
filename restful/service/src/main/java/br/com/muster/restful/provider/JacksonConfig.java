/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.muster.restful.provider;

import java.text.SimpleDateFormat;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;

/**
 *
 * @author macorin
 */
@Provider
public class JacksonConfig implements ContextResolver<ObjectMapper>{

    private final ObjectMapper objectMapper;

    public JacksonConfig() {
        this.objectMapper = new ObjectMapper().configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, false);
        this.objectMapper.setDateFormat(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS"));
    }
    
    @Override
    public ObjectMapper getContext(Class<?> type) {
        return this.objectMapper;
    }
    
}
