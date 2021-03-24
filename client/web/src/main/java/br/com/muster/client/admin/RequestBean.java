/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.muster.client.admin;

import br.com.muster.client.Pagina;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author macorin
 */
@ManagedBean
@RequestScoped
public class RequestBean implements Serializable {

    private static final long serialVersionUID = -1891836229729930715L;

    public String getHome() {
        return Pagina.HOME;
    }

    public String getPerfil() {
        return Pagina.PERFIL;
    }

    public String getEmpresaIndex() {
        return Pagina.EMPRESA_INDEX;
    }

    public String getLicencaIndex() {
        return Pagina.LICENCA_INDEX;
    }

    public String getUsuarioIndex() {
        return Pagina.USUARIO_INDEX;
    }

    public String getPerfilIndex() {
        return Pagina.PERFIL_INDEX;
    }

    public String getSelecionarEmpresaIndex() {
        return Pagina.SELECIONAR_EMPRESA;
    }

    public String getProcessoTableIndex() {
        return Pagina.PROCESSO_TABLE_INDEX;
    }

    public String getTablespaceTableIndex() {
        return Pagina.TABLESPACE_TABLE_INDEX;
    }

    public String getLockTableIndex() {
        return Pagina.LOCK_TABLE_INDEX;
    }

    public String getHitRatioLibraryTableIndex() {
        return Pagina.HIT_RATIO_LIBRARY_TABLE_INDEX;
    }

    public String getHitRatioLatchTableIndex() {
        return Pagina.HIT_RATIO_LATCH_TABLE_INDEX;
    }

    public String getHitRatioDictionaryCacheTableIndex() {
        return Pagina.HIT_RATIO_DICTIONARY_CACHE_TABLE_INDEX;
    }

    public String getHitRatioBufferCacheTableIndex() {
        return Pagina.HIT_RATIO_BUFFER_CACHE_TABLE_INDEX;
    }

    public String getFlashRecoveryAreaTableIndex() {
        return Pagina.FLASH_RECOVERY_AREA_TABLE_INDEX;
    }

}
