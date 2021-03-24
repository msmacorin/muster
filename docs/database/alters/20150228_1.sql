/* ---------------------------------------------------------------------- */
/* Script generated with: DeZign for Databases V8.1.2                     */
/* Target DBMS:           PostgreSQL 9                                    */
/* Project file:          muster.dez                                      */
/* Project name:                                                          */
/* Author:                                                                */
/* Script type:           Alter database script                           */
/* Created on:            2015-02-28 17:35                                */
/* ---------------------------------------------------------------------- */


/* ---------------------------------------------------------------------- */
/* Drop foreign key constraints                                           */
/* ---------------------------------------------------------------------- */

ALTER TABLE processo DROP CONSTRAINT empresa_processo;

ALTER TABLE lock DROP CONSTRAINT empresa_lock;

ALTER TABLE hit_ratio_buffer_cache DROP CONSTRAINT empresa_hit_ratio_buffer_cache;

ALTER TABLE hit_ratio_library_cache DROP CONSTRAINT empresa_hit_ratio_library_cache;

ALTER TABLE hit_ratio_dictionary_cache DROP CONSTRAINT empresa_hit_ratio_dictionary_cache;

ALTER TABLE hit_ratio_latch DROP CONSTRAINT empresa_hit_ratio_latch;

ALTER TABLE flash_recovery_area DROP CONSTRAINT empresa_flash_recovery_area;

ALTER TABLE tablespace DROP CONSTRAINT empresa_tablespace;

/* ---------------------------------------------------------------------- */
/* Alter table "processo"                                                 */
/* ---------------------------------------------------------------------- */

ALTER TABLE processo ALTER COLUMN resultado TYPE NUMERIC(12,4);

/* ---------------------------------------------------------------------- */
/* Alter table "lock"                                                     */
/* ---------------------------------------------------------------------- */

ALTER TABLE lock ALTER COLUMN resultado TYPE NUMERIC(12,4);

/* ---------------------------------------------------------------------- */
/* Alter table "hit_ratio_buffer_cache"                                   */
/* ---------------------------------------------------------------------- */

ALTER TABLE hit_ratio_buffer_cache ALTER COLUMN resultado TYPE NUMERIC(12,4);

/* ---------------------------------------------------------------------- */
/* Alter table "hit_ratio_library_cache"                                  */
/* ---------------------------------------------------------------------- */

ALTER TABLE hit_ratio_library_cache ALTER COLUMN resultado TYPE NUMERIC(12,4);

/* ---------------------------------------------------------------------- */
/* Alter table "hit_ratio_dictionary_cache"                               */
/* ---------------------------------------------------------------------- */

ALTER TABLE hit_ratio_dictionary_cache ALTER COLUMN resultado TYPE NUMERIC(12,4);

/* ---------------------------------------------------------------------- */
/* Alter table "hit_ratio_latch"                                          */
/* ---------------------------------------------------------------------- */

ALTER TABLE hit_ratio_latch ALTER COLUMN resultado TYPE NUMERIC(12,4);

/* ---------------------------------------------------------------------- */
/* Alter table "flash_recovery_area"                                      */
/* ---------------------------------------------------------------------- */

ALTER TABLE flash_recovery_area ALTER COLUMN flash_in_gb TYPE NUMERIC(12,4);

ALTER TABLE flash_recovery_area ALTER COLUMN flash_used_in_gb TYPE NUMERIC(12,4);

ALTER TABLE flash_recovery_area ALTER COLUMN flash_reclaimable_gb TYPE NUMERIC(12,4);

ALTER TABLE flash_recovery_area ALTER COLUMN percent_of_space_used TYPE NUMERIC(12,4);

/* ---------------------------------------------------------------------- */
/* Alter table "tablespace"                                               */
/* ---------------------------------------------------------------------- */

ALTER TABLE tablespace ALTER COLUMN used_mbytes TYPE NUMERIC(12,4);

ALTER TABLE tablespace ALTER COLUMN max_free_mbytes TYPE NUMERIC(12,4);

ALTER TABLE tablespace ALTER COLUMN used_pct TYPE NUMERIC(5,2);

/* ---------------------------------------------------------------------- */
/* Add foreign key constraints                                            */
/* ---------------------------------------------------------------------- */

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
