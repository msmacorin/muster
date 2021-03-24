/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.muster.model;

import java.io.Serializable;

/**
 *
 * @author macorin
 */
public class DataTableColumnModel implements Serializable {
    
    private static final long serialVersionUID = -2518283540600420850L;
    
    private String header;
    private String property;

    public DataTableColumnModel() {
    }

    public DataTableColumnModel(String header, String property) {
        this.header = header;
        this.property = property;
    }
    
    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }
    
}
