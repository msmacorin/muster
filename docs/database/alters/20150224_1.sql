/* ---------------------------------------------------------------------- */
/* Script generated with: DeZign for Databases V8.1.2                     */
/* Target DBMS:           PostgreSQL 9                                    */
/* Project file:          muster.dez                                      */
/* Project name:                                                          */
/* Author:                                                                */
/* Script type:           Alter database script                           */
/* Created on:            2015-02-24 14:40                                */
/* ---------------------------------------------------------------------- */


/* ---------------------------------------------------------------------- */
/* Alter domain "TIPO_PERFIL"                                             */
/* ---------------------------------------------------------------------- */

ALTER DOMAIN TIPO_PERFIL DROP CONSTRAINT TIPO_PERFIL_check;

ALTER DOMAIN TIPO_PERFIL ADD CONSTRAINT TIPO_PERFIL_check CHECK (value in ('ROLE_SUPER', 'ROLE_ADMIN', 'ROLE_USER', 'ROLE_LOCAL'));
