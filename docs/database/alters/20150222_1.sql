/* ---------------------------------------------------------------------- */
/* Script generated with: DeZign for Databases V8.1.2                     */
/* Target DBMS:           PostgreSQL 9                                    */
/* Project file:          muster.dez                                      */
/* Project name:                                                          */
/* Author:                                                                */
/* Script type:           Alter database script                           */
/* Created on:            2015-02-22 17:40                                */
/* ---------------------------------------------------------------------- */


/* ---------------------------------------------------------------------- */
/* Drop foreign key constraints                                           */
/* ---------------------------------------------------------------------- */

ALTER TABLE configuracao_local DROP CONSTRAINT usuario_configuracao_local;

/* ---------------------------------------------------------------------- */
/* Alter table "configuracao_local"                                       */
/* ---------------------------------------------------------------------- */

ALTER TABLE configuracao_local ADD
    id_empresa BIGINT;

/* ---------------------------------------------------------------------- */
/* Add foreign key constraints                                            */
/* ---------------------------------------------------------------------- */

ALTER TABLE configuracao_local ADD CONSTRAINT usuario_configuracao_local 
    FOREIGN KEY (id_usuario) REFERENCES usuario (id_usuario);

ALTER TABLE configuracao_local ADD CONSTRAINT empresa_configuracao_local 
    FOREIGN KEY (id_empresa) REFERENCES empresa (id_empresa);
