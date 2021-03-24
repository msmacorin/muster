/* ---------------------------------------------------------------------- */
/* Script generated with: DeZign for Databases V8.1.2                     */
/* Target DBMS:           PostgreSQL 9                                    */
/* Project file:          muster.dez                                      */
/* Project name:                                                          */
/* Author:                                                                */
/* Script type:           Alter database script                           */
/* Created on:            2015-02-09 21:16                                */
/* ---------------------------------------------------------------------- */


/* ---------------------------------------------------------------------- */
/* Drop foreign key constraints                                           */
/* ---------------------------------------------------------------------- */

ALTER TABLE usuario_perfil DROP CONSTRAINT perfil_usuario_perfil;

ALTER TABLE usuario_perfil DROP CONSTRAINT usuario_usuario_perfil;

ALTER TABLE usuario_empresa DROP CONSTRAINT usuario_usuario_empresa;

ALTER TABLE usuario_empresa DROP CONSTRAINT empresa_usuario_empresa;


/* ---------------------------------------------------------------------- */
/* Add table "configuracao_local"                                         */
/* ---------------------------------------------------------------------- */

CREATE TABLE configuracao_local (
    id_configuracao_local BIGINT  NOT NULL,
    url_api CHARACTER VARYING(100)  NOT NULL,
    e_mail CHARACTER VARYING(50),
    id_usuario BIGINT  NOT NULL,
    CONSTRAINT PK_configuracao_local PRIMARY KEY (id_configuracao_local)
);

/* ---------------------------------------------------------------------- */
/* Add foreign key constraints                                            */
/* ---------------------------------------------------------------------- */

ALTER TABLE usuario_perfil ADD CONSTRAINT perfil_usuario_perfil 
    FOREIGN KEY (id_perfil) REFERENCES perfil (id_perfil);

ALTER TABLE usuario_perfil ADD CONSTRAINT usuario_usuario_perfil 
    FOREIGN KEY (id_usuario) REFERENCES usuario (id_usuario);

ALTER TABLE usuario_empresa ADD CONSTRAINT usuario_usuario_empresa 
    FOREIGN KEY (id_usuario) REFERENCES usuario (id_usuario);

ALTER TABLE usuario_empresa ADD CONSTRAINT empresa_usuario_empresa 
    FOREIGN KEY (id_empresa) REFERENCES empresa (id_empresa);

ALTER TABLE configuracao_local ADD CONSTRAINT usuario_configuracao_local 
    FOREIGN KEY (id_usuario) REFERENCES usuario (id_usuario);
