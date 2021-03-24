package br.com.muster.model.admin;

public class ParametroSistema {

    /**
     * nome atribuido no request para a empresa selecionada.
     */
    public static final String EMPRESA_SESSION = "empresaSession";

    /**
     * tamanho da string de validação da licença.
     */
    public static final int TAMANHO_VALIDACAO_LICENCA = 15;

    /**
     * url padrao para a api
     */
    public static final String URL_API = "http://192.168.10.8:8080/muster-service";

    /**
     * utilizado na criação da autenticação basica para API.
     */
    public static final String AUTHORIZATION_PROPERTY = "Authorization";
    
    /**
     * utilizado na criação da autenticação basica para API.
     */
    public static final String AUTHENTICATION_SCHEME = "Basic";
}
