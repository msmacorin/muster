/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.muster.model.admin;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author macorin
 */
public class Menu {

    public String label;

    private String action;

    private String icon;

    private List<ItemMenu> itensMenu;

    public Menu() {
    }

    public Menu(String label, String action, String icon, List<ItemMenu> itensMenu) {
        this.label = label;
        this.action = action;
        this.icon = icon;
        this.itensMenu = itensMenu;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<ItemMenu> getItensMenu() {
        return itensMenu;
    }

    public void setItensMenu(List<ItemMenu> itensMenu) {
        this.itensMenu = itensMenu;
    }

    public void addItemMenu(ItemMenu item) {
        if (itensMenu == null) {
            itensMenu = new ArrayList<ItemMenu>();
        }

        if (item != null) {
            itensMenu.add(item);
        }
    }
}
