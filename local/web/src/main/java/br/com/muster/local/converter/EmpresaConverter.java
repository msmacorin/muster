/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.muster.local.converter;

import br.com.muster.model.admin.Empresa;
import in.macor.commons.util.StringUtil;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author macorin
 * @see pra poder colocar o EJB aqui tiver que declarar o converter no
 * faces-config.xml e utilizar o converter como managedBean #{empresaConverter}
 *
 */
@FacesConverter(value = "empresaConverter", forClass = Empresa.class)
public class EmpresaConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (!StringUtil.empty(value)) {
            return (Empresa) uic.getAttributes().get(value);
        }

        return null;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            if (object instanceof Empresa) {
                uic.getAttributes().put(((Empresa)object).getId().toString(), object);
                return ((Empresa)object).getId().toString();
            }
        }
        
        return "";
    }
}
