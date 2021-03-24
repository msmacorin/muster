/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.muster.client.converter;

import br.com.muster.model.admin.Usuario;
import in.macor.commons.util.StringUtil;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author macorin
 */
@FacesConverter(value = "usuarioConverter", forClass = Usuario.class)
public class UsuarioConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (!StringUtil.empty(value)) {
            return component.getAttributes().get(value);
        }
        
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null) {
            if (value instanceof Usuario) {
                component.getAttributes().put(((Usuario) value).getId().toString(), value);
                return ((Usuario) value).getId().toString();
            }
        }
        
        return null;
    }
    
}
