/* ---------------------------------------------------------------------- */
/* Script generated with: DeZign for Databases V8.1.2                     */
/* Target DBMS:           PostgreSQL 9                                    */
/* Project file:          muster.dez                                      */
/* Project name:                                                          */
/* Author:                                                                */
/* Script type:           Database creation script                        */
/* Created on:            2015-02-24 15:14                                */
/* ---------------------------------------------------------------------- */


/* ---------------------------------------------------------------------- */
/* Add domains                                                            */
/* ---------------------------------------------------------------------- */

CREATE DOMAIN TIPO_PERFIL AS CHARACTER VARYING(50) CHECK (value in ('ROLE_SUPER', 'ROLE_ADMIN', 'ROLE_USER', 'ROLE_LOCAL'));

CREATE DOMAIN SITUACAO AS INTEGER CHECK (value in (1, 2, 3, 4));

CREATE DOMAIN SIM_NAO AS CHARACTER(1) CHECK (value in ('S', 'N'));

CREATE DOMAIN ENVIO AS INTEGER CHECK (value in (0,1,2));

/* ---------------------------------------------------------------------- */
/* Add tables                                                             */
/* ---------------------------------------------------------------------- */

/* ---------------------------------------------------------------------- */
/* Add table "usuario"                                                    */
/* ---------------------------------------------------------------------- */

CREATE TABLE usuario (
    id_usuario UUID  NOT NULL,
    nome CHARACTER VARYING(50)  NOT NULL,
    e_mail CHARACTER VARYING(40)  NOT NULL,
    login CHARACTER VARYING(50)  NOT NULL,
    senha CHARACTER VARYING(50)  NOT NULL,
    CONSTRAINT PK_usuario PRIMARY KEY (id_usuario),
    CONSTRAINT TUC_usuario_1 UNIQUE (login)
);

CREATE INDEX IDX_usuario_1 ON usuario (login);

/* ---------------------------------------------------------------------- */
/* Add table "perfil"                                                     */
/* ---------------------------------------------------------------------- */

CREATE TABLE perfil (
    id_perfil UUID  NOT NULL,
    nome CHARACTER VARYING(50),
    tipo_perfil TIPO_PERFIL  NOT NULL,
    CONSTRAINT PK_perfil PRIMARY KEY (id_perfil),
    CONSTRAINT TUC_perfil_1 UNIQUE (nome)
);

/* ---------------------------------------------------------------------- */
/* Add table "empresa"                                                    */
/* ---------------------------------------------------------------------- */

CREATE TABLE empresa (
    id_empresa UUID  NOT NULL,
    nome CHARACTER VARYING(50)  NOT NULL,
    cnpj CHARACTER VARYING(15)  NOT NULL,
    e_mail CHARACTER VARYING(50)  NOT NULL,
    situacao SITUACAO  NOT NULL,
    data_inicio DATE  NOT NULL,
    CONSTRAINT PK_empresa PRIMARY KEY (id_empresa)
);

/* ---------------------------------------------------------------------- */
/* Add table "licenca"                                                    */
/* ---------------------------------------------------------------------- */

CREATE TABLE licenca (
    id_licenca UUID  NOT NULL,
    data_inicio DATE  NOT NULL,
    prazo INTEGER  NOT NULL,
    validacao CHARACTER VARYING(50)  NOT NULL,
    situacao SITUACAO  NOT NULL,
    id_empresa UUID,
    CONSTRAINT PK_licenca PRIMARY KEY (id_licenca)
);

/* ---------------------------------------------------------------------- */
/* Add table "usuario_perfil"                                             */
/* ---------------------------------------------------------------------- */

CREATE TABLE usuario_perfil (
    id_perfil UUID  NOT NULL,
    id_usuario UUID  NOT NULL,
    CONSTRAINT PK_usuario_perfil PRIMARY KEY (id_perfil, id_usuario)
);

/* ---------------------------------------------------------------------- */
/* Add table "usuario_empresa"                                            */
/* ---------------------------------------------------------------------- */

CREATE TABLE usuario_empresa (
    id_usuario UUID  NOT NULL,
    id_empresa UUID  NOT NULL,
    CONSTRAINT PK_usuario_empresa PRIMARY KEY (id_usuario, id_empresa)
);

COMMENT ON TABLE usuario_empresa IS 'quando o usuario é uma secretaria ou medico, por exemplo, vai poder estar vinculado a uma ou mais empresas.';

/* ---------------------------------------------------------------------- */
/* Add table "processo"                                                   */
/* ---------------------------------------------------------------------- */

CREATE TABLE processo (
    id_processo UUID  NOT NULL,
    data TIMESTAMP  NOT NULL,
    resultado NUMERIC(6,4)  NOT NULL,
    envio ENVIO  NOT NULL,
    id_empresa UUID  NOT NULL,
    CONSTRAINT PK_processo PRIMARY KEY (id_processo)
);

/* ---------------------------------------------------------------------- */
/* Add table "lock"                                                       */
/* ---------------------------------------------------------------------- */

CREATE TABLE lock (
    id_lock UUID  NOT NULL,
    data TIMESTAMP  NOT NULL,
    resultado NUMERIC(6,4)  NOT NULL,
    envio ENVIO  NOT NULL,
    id_empresa UUID  NOT NULL,
    CONSTRAINT PK_lock PRIMARY KEY (id_lock)
);

/* ---------------------------------------------------------------------- */
/* Add table "hit_ratio_buffer_cache"                                     */
/* ---------------------------------------------------------------------- */

CREATE TABLE hit_ratio_buffer_cache (
    id_hit_ratio_buffer_cache UUID  NOT NULL,
    data TIMESTAMP  NOT NULL,
    resultado NUMERIC(6,4)  NOT NULL,
    envio ENVIO  NOT NULL,
    id_empresa UUID  NOT NULL,
    PRIMARY KEY (id_hit_ratio_buffer_cache)
);

/* ---------------------------------------------------------------------- */
/* Add table "hit_ratio_library_cache"                                    */
/* ---------------------------------------------------------------------- */

CREATE TABLE hit_ratio_library_cache (
    id_hit_ratio_library_cache UUID  NOT NULL,
    data TIMESTAMP  NOT NULL,
    resultado NUMERIC(6,4)  NOT NULL,
    envio ENVIO  NOT NULL,
    id_empresa UUID  NOT NULL,
    PRIMARY KEY (id_hit_ratio_library_cache)
);

/* ---------------------------------------------------------------------- */
/* Add table "hit_ratio_dictionary_cache"                                 */
/* ---------------------------------------------------------------------- */

CREATE TABLE hit_ratio_dictionary_cache (
    id_hit_ratio_dictionary_cache UUID  NOT NULL,
    data TIMESTAMP  NOT NULL,
    resultado NUMERIC(6,4)  NOT NULL,
    envio ENVIO  NOT NULL,
    id_empresa UUID  NOT NULL,
    PRIMARY KEY (id_hit_ratio_dictionary_cache)
);

/* ---------------------------------------------------------------------- */
/* Add table "hit_ratio_latch"                                            */
/* ---------------------------------------------------------------------- */

CREATE TABLE hit_ratio_latch (
    id_hit_ratio_latch UUID  NOT NULL,
    data TIMESTAMP  NOT NULL,
    resultado NUMERIC(6,4)  NOT NULL,
    envio ENVIO  NOT NULL,
    id_empresa UUID  NOT NULL,
    PRIMARY KEY (id_hit_ratio_latch)
);

/* ---------------------------------------------------------------------- */
/* Add table "flash_recovery_area"                                        */
/* ---------------------------------------------------------------------- */

CREATE TABLE flash_recovery_area (
    id_flash_recovery_area UUID  NOT NULL,
    data TIMESTAMP  NOT NULL,
    flash_in_gb NUMERIC(6,4)  NOT NULL,
    flash_used_in_gb NUMERIC(6,4)  NOT NULL,
    flash_reclaimable_gb NUMERIC(6,4)  NOT NULL,
    percent_of_space_used NUMERIC(6,4)  NOT NULL,
    envio ENVIO  NOT NULL,
    id_empresa UUID  NOT NULL,
    CONSTRAINT PK_flash_recovery_area PRIMARY KEY (id_flash_recovery_area)
);

/* ---------------------------------------------------------------------- */
/* Add table "tablespace"                                                 */
/* ---------------------------------------------------------------------- */

CREATE TABLE tablespace (
    id_tablespace UUID  NOT NULL,
    data TIMESTAMP  NOT NULL,
    tablespace_name CHARACTER VARYING(100)  NOT NULL,
    used_mbytes NUMERIC(6,4)  NOT NULL,
    max_free_mbytes NUMERIC(6,4)  NOT NULL,
    used_pct NUMERIC(3,2)  NOT NULL,
    envio ENVIO  NOT NULL,
    id_empresa UUID  NOT NULL,
    CONSTRAINT PK_tablespace PRIMARY KEY (id_tablespace)
);

/* ---------------------------------------------------------------------- */
/* Add table "configuracao_local"                                         */
/* ---------------------------------------------------------------------- */

CREATE TABLE configuracao_local (
    id_configuracao_local BIGINT  NOT NULL,
    url_api CHARACTER VARYING(100)  NOT NULL,
    e_mail CHARACTER VARYING(50),
    executar_servicos SIM_NAO  NOT NULL,
    id_usuario UUID  NOT NULL,
    id_empresa UUID,
    CONSTRAINT PK_configuracao_local PRIMARY KEY (id_configuracao_local)
);

/* ---------------------------------------------------------------------- */
/* Add foreign key constraints                                            */
/* ---------------------------------------------------------------------- */

ALTER TABLE licenca ADD CONSTRAINT empresa_licenca 
    FOREIGN KEY (id_empresa) REFERENCES empresa (id_empresa);

ALTER TABLE usuario_perfil ADD CONSTRAINT usuario_usuario_perfil 
    FOREIGN KEY (id_usuario) REFERENCES usuario (id_usuario);

ALTER TABLE usuario_perfil ADD CONSTRAINT perfil_usuario_perfil 
    FOREIGN KEY (id_perfil) REFERENCES perfil (id_perfil);

ALTER TABLE usuario_empresa ADD CONSTRAINT usuario_usuario_empresa 
    FOREIGN KEY (id_usuario) REFERENCES usuario (id_usuario);

ALTER TABLE usuario_empresa ADD CONSTRAINT empresa_usuario_empresa 
    FOREIGN KEY (id_empresa) REFERENCES empresa (id_empresa);

ALTER TABLE processo ADD CONSTRAINT empresa_processo 
    FOREIGN KEY (id_empresa) REFERENCES empresa (id_empresa);

ALTER TABLE lock ADD CONSTRAINT empresa_lock 
    FOREIGN KEY (id_empresa) REFERENCES empresa (id_empresa);

ALTER TABLE hit_ratio_buffer_cache ADD CONSTRAINT empresa_hit_ratio_buffer_cache 
    FOREIGN KEY (id_empresa) REFERENCES empresa (id_empresa);

ALTER TABLE hit_ratio_library_cache ADD CONSTRAINT empresa_hit_ratio_library_cache 
    FOREIGN KEY (id_empresa) REFERENCES empresa (id_empresa);

ALTER TABLE hit_ratio_dictionary_cache ADD CONSTRAINT empresa_hit_ratio_dictionary_cache 
    FOREIGN KEY (id_empresa) REFERENCES empresa (id_empresa);

ALTER TABLE hit_ratio_latch ADD CONSTRAINT empresa_hit_ratio_latch 
    FOREIGN KEY (id_empresa) REFERENCES empresa (id_empresa);

ALTER TABLE flash_recovery_area ADD CONSTRAINT empresa_flash_recovery_area 
    FOREIGN KEY (id_empresa) REFERENCES empresa (id_empresa);

ALTER TABLE tablespace ADD CONSTRAINT empresa_tablespace 
    FOREIGN KEY (id_empresa) REFERENCES empresa (id_empresa);

ALTER TABLE configuracao_local ADD CONSTRAINT usuario_configuracao_local 
    FOREIGN KEY (id_usuario) REFERENCES usuario (id_usuario);

ALTER TABLE configuracao_local ADD CONSTRAINT empresa_configuracao_local 
    FOREIGN KEY (id_empresa) REFERENCES empresa (id_empresa);

insert into perfil (id_perfil, nome, tipo_perfil) values ('b5cfc62d-11b4-6027-72e9-c832aed077b3', 'Super usuario', 'ROLE_SUPER');
insert into perfil (id_perfil, nome, tipo_perfil) values ('ab5bd07f-c43a-f203-2d2b-97de7d298688', 'Administrador do sistema', 'ROLE_ADMIN');
insert into perfil (id_perfil, nome, tipo_perfil) values ('ba4c021d-0cde-de2a-b275-5cc7fb780a7d', 'Usuario comum', 'ROLE_USER');
insert into perfil (id_perfil, nome, tipo_perfil) values ('a829b926-7566-c85f-564a-951a194504e2', 'Administrador local do sistema', 'ROLE_LOCAL');
