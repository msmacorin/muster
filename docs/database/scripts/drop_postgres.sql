/* ---------------------------------------------------------------------- */
/* Script generated with: DeZign for Databases V8.1.2                     */
/* Target DBMS:           PostgreSQL 9                                    */
/* Project file:          muster.dez                                      */
/* Project name:                                                          */
/* Author:                                                                */
/* Script type:           Database drop script                            */
/* Created on:            2015-02-24 15:14                                */
/* ---------------------------------------------------------------------- */


/* ---------------------------------------------------------------------- */
/* Drop foreign key constraints                                           */
/* ---------------------------------------------------------------------- */

ALTER TABLE licenca DROP CONSTRAINT empresa_licenca;

ALTER TABLE usuario_perfil DROP CONSTRAINT usuario_usuario_perfil;

ALTER TABLE usuario_perfil DROP CONSTRAINT perfil_usuario_perfil;

ALTER TABLE usuario_empresa DROP CONSTRAINT usuario_usuario_empresa;

ALTER TABLE usuario_empresa DROP CONSTRAINT empresa_usuario_empresa;

ALTER TABLE processo DROP CONSTRAINT empresa_processo;

ALTER TABLE lock DROP CONSTRAINT empresa_lock;

ALTER TABLE hit_ratio_buffer_cache DROP CONSTRAINT empresa_hit_ratio_buffer_cache;

ALTER TABLE hit_ratio_library_cache DROP CONSTRAINT empresa_hit_ratio_library_cache;

ALTER TABLE hit_ratio_dictionary_cache DROP CONSTRAINT empresa_hit_ratio_dictionary_cache;

ALTER TABLE hit_ratio_latch DROP CONSTRAINT empresa_hit_ratio_latch;

ALTER TABLE flash_recovery_area DROP CONSTRAINT empresa_flash_recovery_area;

ALTER TABLE tablespace DROP CONSTRAINT empresa_tablespace;

ALTER TABLE configuracao_local DROP CONSTRAINT usuario_configuracao_local;

ALTER TABLE configuracao_local DROP CONSTRAINT empresa_configuracao_local;

/* ---------------------------------------------------------------------- */
/* Drop table "configuracao_local"                                        */
/* ---------------------------------------------------------------------- */

/* Drop constraints */

ALTER TABLE configuracao_local DROP CONSTRAINT PK_configuracao_local;

DROP TABLE configuracao_local;

/* ---------------------------------------------------------------------- */
/* Drop table "tablespace"                                                */
/* ---------------------------------------------------------------------- */

/* Drop constraints */

ALTER TABLE tablespace DROP CONSTRAINT PK_tablespace;

DROP TABLE tablespace;

/* ---------------------------------------------------------------------- */
/* Drop table "flash_recovery_area"                                       */
/* ---------------------------------------------------------------------- */

/* Drop constraints */

ALTER TABLE flash_recovery_area DROP CONSTRAINT PK_flash_recovery_area;

DROP TABLE flash_recovery_area;

/* ---------------------------------------------------------------------- */
/* Drop table "hit_ratio_latch"                                           */
/* ---------------------------------------------------------------------- */

/* Drop constraints */

ALTER TABLE hit_ratio_latch DROP CONSTRAINT ;

DROP TABLE hit_ratio_latch;

/* ---------------------------------------------------------------------- */
/* Drop table "hit_ratio_dictionary_cache"                                */
/* ---------------------------------------------------------------------- */

/* Drop constraints */

ALTER TABLE hit_ratio_dictionary_cache DROP CONSTRAINT ;

DROP TABLE hit_ratio_dictionary_cache;

/* ---------------------------------------------------------------------- */
/* Drop table "hit_ratio_library_cache"                                   */
/* ---------------------------------------------------------------------- */

/* Drop constraints */

ALTER TABLE hit_ratio_library_cache DROP CONSTRAINT ;

DROP TABLE hit_ratio_library_cache;

/* ---------------------------------------------------------------------- */
/* Drop table "hit_ratio_buffer_cache"                                    */
/* ---------------------------------------------------------------------- */

/* Drop constraints */

ALTER TABLE hit_ratio_buffer_cache DROP CONSTRAINT ;

DROP TABLE hit_ratio_buffer_cache;

/* ---------------------------------------------------------------------- */
/* Drop table "lock"                                                      */
/* ---------------------------------------------------------------------- */

/* Drop constraints */

ALTER TABLE lock DROP CONSTRAINT PK_lock;

DROP TABLE lock;

/* ---------------------------------------------------------------------- */
/* Drop table "processo"                                                  */
/* ---------------------------------------------------------------------- */

/* Drop constraints */

ALTER TABLE processo DROP CONSTRAINT PK_processo;

DROP TABLE processo;

/* ---------------------------------------------------------------------- */
/* Drop table "usuario_empresa"                                           */
/* ---------------------------------------------------------------------- */

/* Drop constraints */

ALTER TABLE usuario_empresa DROP CONSTRAINT PK_usuario_empresa;

DROP TABLE usuario_empresa;

/* ---------------------------------------------------------------------- */
/* Drop table "usuario_perfil"                                            */
/* ---------------------------------------------------------------------- */

/* Drop constraints */

ALTER TABLE usuario_perfil DROP CONSTRAINT PK_usuario_perfil;

DROP TABLE usuario_perfil;

/* ---------------------------------------------------------------------- */
/* Drop table "licenca"                                                   */
/* ---------------------------------------------------------------------- */

/* Drop constraints */

ALTER TABLE licenca DROP CONSTRAINT PK_licenca;

DROP TABLE licenca;

/* ---------------------------------------------------------------------- */
/* Drop table "empresa"                                                   */
/* ---------------------------------------------------------------------- */

/* Drop constraints */

ALTER TABLE empresa DROP CONSTRAINT PK_empresa;

DROP TABLE empresa;

/* ---------------------------------------------------------------------- */
/* Drop table "perfil"                                                    */
/* ---------------------------------------------------------------------- */

/* Drop constraints */

ALTER TABLE perfil DROP CONSTRAINT PK_perfil;

ALTER TABLE perfil DROP CONSTRAINT TUC_perfil_1;

DROP TABLE perfil;

/* ---------------------------------------------------------------------- */
/* Drop table "usuario"                                                   */
/* ---------------------------------------------------------------------- */

/* Drop constraints */

ALTER TABLE usuario DROP CONSTRAINT PK_usuario;

ALTER TABLE usuario DROP CONSTRAINT TUC_usuario_1;

DROP TABLE usuario;

/* ---------------------------------------------------------------------- */
/* Drop domains                                                           */
/* ---------------------------------------------------------------------- */

DROP DOMAIN TIPO_PERFIL;

DROP DOMAIN SITUACAO;

DROP DOMAIN SIM_NAO;

DROP DOMAIN ENVIO;
