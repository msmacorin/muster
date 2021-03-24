/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.muster.local.service;

import br.com.muster.facade.servico.HitRatioLatchFacade;
import br.com.muster.model.servico.HitRatioLatch;
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
public class HitRatioLatchService implements Service {

    @EJB
    private HitRatioLatchFacade hitRatioLatchFacade;

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
        return "HIT_RATIO_LATCH";
    }

    @Override
    public void removeOlds() {
        hitRatioLatchFacade.removerEnviadosAntigos();
    }

    @Override
    public void processAgain() {
        for (HitRatioLatch l : hitRatioLatchFacade.registrosPendentes()) {
            hitRatioLatchFacade.doPost(l);
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
            res = sta.executeQuery("SELECT (1 - (Sum(misses) / Sum(gets))) * 100 resultado FROM   v$latch");

            if (res.next()) {
                BigDecimal resultado = res.getBigDecimal("resultado");
                
                HitRatioLatch lat = new HitRatioLatch();
                lat.setResultado(resultado.setScale(4, RoundingMode.HALF_UP));
                lat.setData(new Date());
                
                hitRatioLatchFacade.doPost(lat);
            }
        } catch (SQLException ex) {
            Logger.getLogger(HitRatioLatchService.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (res != null) {
                try {
                    res.close();
                } catch (SQLException ex) {
                    Logger.getLogger(HitRatioLatchService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if (sta != null) {
                try {
                    sta.close();
                } catch (SQLException ex) {
                    Logger.getLogger(HitRatioLatchService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if (con != null) {
                try {
                    con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(HitRatioLatchService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

}
