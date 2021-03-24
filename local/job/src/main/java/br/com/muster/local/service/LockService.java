/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.muster.local.service;

import br.com.muster.facade.servico.LockFacade;
import br.com.muster.model.servico.Lock;
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
public class LockService implements Service {

    @EJB
    private LockFacade lockFacade;
    
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
        return "LOCK";
    }

    @Override
    public void removeOlds() {
        lockFacade.removerEnviadosAntigos();
    }

    @Override
    public void processAgain() {
        for (Lock l : lockFacade.registrosPendentes()) {
            lockFacade.doPost(l);
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
            res = sta.executeQuery(""
                    + "WITH sessions AS "
                    + "       (SELECT /*+ materialize*/ username,sid,sql_id "
                    + "          FROM v$session), "
                    + "     locks AS "
                    + "        (SELECT /*+ materialize */ *"
                    + "           FROM v$lock) "
                    + "SELECT count(*) as resultado "
                    + "  FROM locks l1 "
                    + "  JOIN locks l2 USING (id1, id2) "
                    + "  JOIN sessions s1 ON (s1.sid = l1.sid) "
                    + "  JOIN sessions s2 ON (s2.sid = l2.sid) "
                    + "  LEFT OUTER JOIN  v$sql sq "
                    + "       ON (sq.sql_id = s2.sql_id) "
                    + " WHERE l1.BLOCK = 1 AND l2.request > 0");
            
            if (res.next()) {
                BigDecimal resultado = res.getBigDecimal("resultado");
                
                Lock lock = new Lock();
                lock.setResultado(resultado.setScale(4, RoundingMode.HALF_UP));
                lock.setData(new Date());
                
                lockFacade.doPost(lock);
            }
        } catch (SQLException ex) {
            Logger.getLogger(LockService.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (res != null) {
                try {
                    res.close();
                } catch (SQLException ex) {
                    Logger.getLogger(LockService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if (sta != null) {
                try {
                    sta.close();
                } catch (SQLException ex) {
                    Logger.getLogger(LockService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if (con != null) {
                try {
                    con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(LockService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

}
