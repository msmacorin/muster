/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.muster.local.service;

import br.com.muster.facade.servico.HitRatioBufferCacheFacade;
import br.com.muster.model.servico.HitRatioBufferCache;
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
public class HitRatioBufferCacheService implements Service {

    @EJB
     private HitRatioBufferCacheFacade hitRatioBufferCacheFacade;       
    
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
        return "HIT_RATIO_BUFFER_CACHE";
    }

    @Override
    public void removeOlds() {
        hitRatioBufferCacheFacade.removerEnviadosAntigos();
    }

    @Override
    public void processAgain() {
        for (HitRatioBufferCache b : hitRatioBufferCacheFacade.registrosPendentes()) {
            hitRatioBufferCacheFacade.doPost(b);
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
            res = sta.executeQuery("SELECT ( 1 - ( Sum(Decode(NAME, 'physical reads', value, "
                    + "                                0)) / ( Sum(Decode(NAME, 'db block gets', value, "
                    + "                                        0)) "
                    + "                                        + Sum(Decode(NAME, 'consistent gets', "
                    + "                                        value, 0) "
                    + "                                        ) "
                    + "                                      ) ) ) * 100 resultado "
                    + "FROM   v$sysstat");

            if (res.next()) {
                BigDecimal resultado = res.getBigDecimal("resultado");
                
                HitRatioBufferCache buf = new HitRatioBufferCache();
                buf.setResultado(resultado.setScale(4, RoundingMode.HALF_UP));
                buf.setData(new Date());
                
                hitRatioBufferCacheFacade.doPost(buf);
            }
        } catch (SQLException ex) {
            Logger.getLogger(HitRatioBufferCacheService.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (res != null) {
                try {
                    res.close();
                } catch (SQLException ex) {
                    Logger.getLogger(HitRatioBufferCacheService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if (sta != null) {
                try {
                    sta.close();
                } catch (SQLException ex) {
                    Logger.getLogger(HitRatioBufferCacheService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if (con != null) {
                try {
                    con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(HitRatioBufferCacheService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

}
