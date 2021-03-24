/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.muster.local.admin;

import br.com.muster.facade.admin.ConfiguracaoLocalFacade;
import br.com.muster.facade.admin.EmpresaFacade;
import br.com.muster.facade.admin.UsuarioFacade;
import br.com.muster.local.AbstractCrudBean;
import br.com.muster.local.Pagina;
import br.com.muster.model.DataTableColumnModel;
import br.com.muster.model.admin.ConfiguracaoLocal;
import br.com.muster.model.admin.Empresa;
import br.com.muster.model.admin.ParametroSistema;
import br.com.muster.model.admin.Usuario;
import br.com.muster.model.enums.ESimNao;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;

/**
 *
 * @author macorin
 */
@ManagedBean
@ViewScoped
public class ConfiguracaoLocalBean extends AbstractCrudBean<ConfiguracaoLocal> {

    @EJB
    private ConfiguracaoLocalFacade configuracaoLocalFacade;

    @EJB
    private UsuarioFacade usuarioFacade;

    @EJB
    private EmpresaFacade empresaFacade;

    private ConfiguracaoLocal configuracaoLocal;

    private Usuario usuario;
    private Empresa empresa;
    private boolean empresaNaoSelecionada;

    // mostra apenas as empresas que possuem licenças.
    private List<Empresa> empresasLicenciadas;

    // mostra apenas os usuarios vinculados a empresa selecionada.
    private List<Usuario> usuariosVinculados;

    @PostConstruct
    public void init() {
        configuracaoLocal = configuracaoLocalFacade.buscarPorId(1L);

        if (configuracaoLocal == null) {
            configuracaoLocal = new ConfiguracaoLocal();
            configuracaoLocal.setId(1L);
            configuracaoLocal.setExecutarServicos(ESimNao.Nao);
            configuracaoLocal.setUrlApi(ParametroSistema.URL_API);
        }

        empresasLicenciadas = empresaFacade.empresasLicenciadasRemoto(session.getUsuario());
        usuariosVinculados = new ArrayList<>();
        empresaNaoSelecionada = true;
    }

    @Override
    protected String[] getFieldsListBean() {
        return null;
    }

    @Override
    protected void excluir() throws EJBException {
    }

    @Override
    protected void salvar() throws EJBException {
        if (empresaFacade.buscarPorId(empresa.getId()) == null) {
            empresaFacade.salvar(empresa);
        }

        if (usuarioFacade.buscarPorId(usuario.getId()) == null) {
            usuario.setEmpresas(null);
            usuario.setPerfis(null);

            usuarioFacade.salvar(usuario);
        }

        configuracaoLocal.setUsuario(usuario);
        configuracaoLocal.setEmpresa(empresa);
        configuracaoLocalFacade.salvar(configuracaoLocal);
    }

    @Override
    protected List<ConfiguracaoLocal> registros() {
        return null;
    }

    @Override
    protected String getPaginaInicial() {
        return Pagina.CONFIGURACAO_LOCAL;
    }

    @Override
    public List<DataTableColumnModel> getColumnModel() {
        return null;
    }

    @Override
    public ConfiguracaoLocal getValue() {
        return configuracaoLocal;
    }

    @Override
    public void setValue(ConfiguracaoLocal value) {
        this.configuracaoLocal = value;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void getUsuariosRemoto() {
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public List<Empresa> getEmpresasLicenciadas() {
        return empresasLicenciadas;
    }

    public void setEmpresasLicenciadas(List<Empresa> empresasLicenciadas) {
        this.empresasLicenciadas = empresasLicenciadas;
    }

    public boolean isEmpresaNaoSelecionada() {
        return empresaNaoSelecionada;
    }

    public void setEmpresaNaoSelecionada(boolean empresaNaoSelecionada) {
        this.empresaNaoSelecionada = empresaNaoSelecionada;
    }

    public void alterarEmpresa(ValueChangeEvent event) {
        if (event.getNewValue() instanceof Empresa) {
            empresaNaoSelecionada = false;
            setUsuariosVinculados(usuarioFacade.getUsuariosDaEmpresaRemoto(session.getUsuario(),
                    ((Empresa) event.getNewValue()).getId()));
        } else {
            empresaNaoSelecionada = true;
        }
    }

    public void alterarEmpresa2() {
        empresaNaoSelecionada = !empresaNaoSelecionada;
    }

    public List<Usuario> getUsuariosVinculados() {
        return usuariosVinculados;
    }

    public void setUsuariosVinculados(List<Usuario> usuariosVinculados) {
        this.usuariosVinculados = usuariosVinculados;
    }
}
