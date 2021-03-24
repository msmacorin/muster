/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.muster.local.service;

import br.com.muster.facade.servico.TablespaceFacade;
import br.com.muster.model.servico.Tablespace;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.ScheduleExpression;
import javax.ejb.Stateless;
import javax.ejb.TimerConfig;
import javax.sql.DataSource;

/**
 *
 * @author macorin
 */
@Stateless
@LocalBean
public class TablespaceService implements Service {

    @EJB
    private TablespaceFacade tablespaceFacade;

    @Override
    public TimerConfig getTimerConfig() {
        return new TimerConfig(getName(), false);
    }

    @Override
    public List<ScheduleExpression> getSchedulesExpression() {
        List<ScheduleExpression> expressions = new LinkedList<>();
        expressions.add(new ScheduleExpression().hour("*").minute("*/10"));

        return expressions;
    }

    @Override
    public String getName() {
        return "TABLESPACE";
    }

    @Override
    public void removeOlds() {
        tablespaceFacade.removerEnviadosAntigos();
    }

    @Override
    public void processAgain() {
        for (Tablespace t : tablespaceFacade.registrosPendentes()) {
            tablespaceFacade.doPost(t);
        }
    }

    @Override
    public void execute(DataSource dataSource) {
        Statement sta = null;
        ResultSet res = null;
        Connection con = null;

        try {
            con = dataSource.getConnection();
            sta = con.createStatement();
            res = sta.executeQuery("SELECT d.tablespace_name, "
                    + "       Round(u.bytes / 1024 / 1024, 2)             used_mbytes, "
                    + "       Round(( d.max - u.bytes ) / 1024 / 1024, 2) AS max_free_mbytes, "
                    + "       Round(u.bytes * 100 / d.max, 2)             AS used_pct "
                    + "FROM   sys.sm$ts_used u, "
                    + "       (SELECT tablespace_name, "
                    + "               Sum(Decode(maxbytes, 0, bytes, "
                    + "                                    maxbytes)) max "
                    + "        FROM   sys.dba_data_files "
                    + "        GROUP  BY tablespace_name) d "
                    + "WHERE  u.tablespace_name = d.tablespace_name ");

            while (res.next()) {
                String name = res.getString("tablespace_name");
                BigDecimal used = res.getBigDecimal("used_mbytes");
                BigDecimal free = res.getBigDecimal("max_free_mbytes");
                BigDecimal pct = res.getBigDecimal("used_pct");
                
                Tablespace tablespace = new Tablespace();

                tablespace.setTablespaceName(name);
                tablespace.setUsedMbytes(used.setScale(4, RoundingMode.HALF_UP));
                tablespace.setMaxFreeMbytes(free.setScale(4, RoundingMode.HALF_UP));
                tablespace.setUsedPct(pct.setScale(2, RoundingMode.HALF_UP));
                tablespace.setData(new Date());
                
                tablespaceFacade.doPost(tablespace);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TablespaceService.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (res != null) {
                try {
                    res.close();
                } catch (SQLException ex) {
                    Logger.getLogger(TablespaceService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if (sta != null) {
                try {
                    sta.close();
                } catch (SQLException ex) {
                    Logger.getLogger(TablespaceService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if (con != null) {
                try {
                    con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(TablespaceService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

}
