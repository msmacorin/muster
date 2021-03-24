/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.muster.local;

import br.com.muster.local.admin.UserSession;
import in.macor.commons.iface.IEnums;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author macorin
 */
public abstract class AbstractBean {

    @Inject
    protected UserSession session;

    protected Logger logger = LoggerFactory.getLogger(getClass());

    private final ServletContext context = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();

    protected ServletContext getContext() {
        return context;
    }

    protected void messages(FacesMessage message) {
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    protected String redirect(String url) {
        String redirect = "?faces-redirect=true";
        return url + redirect;
    }

    protected String getString(String key) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ResourceBundle bundle = facesContext.getApplication().getResourceBundle(facesContext, "msg");

        return bundle.getString(key);
    }
//
//    protected Map<String, String> doSelectOneMenu(IEnums[] values) {
//        Map<String, String> select = new LinkedHashMap<>();
//        for (IEnums e : values) {
//            select.put(getString(e.getDescricao()), e.getCodigo().toString());
//        }
//
//        return select;
//    }
    
    protected <M> Map<String, M> doSelectOneMenu(M[] values) {
        Map<String, M> select = new LinkedHashMap<>();
        for (M e : values) {
            select.put(getString(((IEnums) e).getDescricao()), e);
        }

        return select;
    }
}
